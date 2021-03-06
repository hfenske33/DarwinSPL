package de.darwinspl.importer.ui.wizards.evolution.darwinspl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import de.christophseidl.util.eclipse.ResourceUtil;
import de.christophseidl.util.ecore.EcoreIOUtil;
import de.darwinspl.importer.FeatureModelConstraintsTuple;
import de.darwinspl.importer.evolution.DwFeatureModelEvolutionImporter;
import de.darwinspl.importer.ui.wizards.DwFeatureModelEvolutionStepWizardPage;
import de.darwinspl.importer.ui.wizards.DwFeatureModelWizardImportedFilePage;
import de.darwinspl.importer.ui.wizards.DwMultiFileSelectionWizardPage;
import de.darwinspl.importer.ui.wizards.FileSelectionWizardPage;
import eu.hyvar.feature.HyFeatureModel;
import eu.hyvar.feature.constraint.HyConstraintModel;
import eu.hyvar.feature.constraint.util.HyConstraintIOUtil;
import eu.hyvar.feature.constraint.util.HyConstraintUtil;

public class DwEvolutionSnapshotImporterWizard extends Wizard implements IImportWizard {
	protected FileSelectionWizardPage tfmFileSelectionWizardPage;
	protected DwFeatureModelWizardImportedFilePage dwFeatureModelWizardImportedFilePage;
	protected DwFeatureModelEvolutionStepWizardPage featureModelEvolutionStepWizardPage;
	
	protected IStructuredSelection selection;
	protected IWorkbench workbench;
	
	public DwEvolutionSnapshotImporterWizard() {
		super();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		
		Object firstSelection = selection.getFirstElement();
		IFile selectedFile = null;
		if(firstSelection instanceof IFile) {
			selectedFile = (IFile) firstSelection;
		}
		
		setWindowTitle("New Feature Model");
		
		tfmFileSelectionWizardPage = new DwMultiFileSelectionWizardPage("Evolution Snapshot Importer", "Please Select multiple Temporal Feature Models for Import" , new String[] {"*.hyfeature", "*.dwfeature"}, "Temporal Feature Model", selectedFile);
		
		featureModelEvolutionStepWizardPage = new DwFeatureModelEvolutionStepWizardPage("Feature Model Evolution Step Association", null);
		
		dwFeatureModelWizardImportedFilePage = new DwFeatureModelWizardImportedFilePage("Select new File to store the imported Feature Model", selection);
		
	}

	@Override
	public boolean performFinish() {	
		StringBuilder statisticsStringBuilder = new StringBuilder("Import statistics:");	
		
		Map<FeatureModelConstraintsTuple, Date> featureModelDateMap = new HashMap<FeatureModelConstraintsTuple, Date>();
		
		Map<IFile, Date> fileDateMap = featureModelEvolutionStepWizardPage.getFileDateMap();
		
		ResourceSet rs = null;
		
		System.out.println("Loading Models");
		for(Entry<IFile, Date> fileDate : fileDateMap.entrySet()) {
			statisticsStringBuilder.append(System.lineSeparator());
			statisticsStringBuilder.append("Model at date "+fileDate.getValue()+":");
			
			if(fileDate.getKey() != null) {
				System.out.println("Loading Model "+fileDate.getKey().getName());
				long start = System.currentTimeMillis();
				EObject loadedObject = null;
				if(rs == null) {
					loadedObject = EcoreIOUtil.loadModel(fileDate.getKey());
					rs = loadedObject.eResource().getResourceSet();
				}
				else {
					loadedObject = EcoreIOUtil.loadModel(fileDate.getKey(), rs);
				}
				
				if(loadedObject == null) {
					System.err.println("Could not load selected file as model");
					return false;
				}
				
				if(!(loadedObject instanceof HyFeatureModel)) {
					System.err.println("Loaded file "+fileDate.getKey()+" is no Temporal Feature Model");
					return false;
				}
				
				HyFeatureModel loadedFeatureModel = (HyFeatureModel) loadedObject;
				HyConstraintModel loadedConstraintModel = HyConstraintIOUtil.loadAccompanyingConstraintModel(loadedFeatureModel);
				
				long end = System.currentTimeMillis();
				System.out.println("Loading Model took "+(end-start)+ " milliseconds.");
				
				statisticsStringBuilder.append(System.lineSeparator());
				statisticsStringBuilder.append("#features: ");
				statisticsStringBuilder.append(loadedFeatureModel.getFeatures().size());
				
				statisticsStringBuilder.append(System.lineSeparator());
				statisticsStringBuilder.append("#groups: ");
				statisticsStringBuilder.append(loadedFeatureModel.getGroups().size());
				
				if(loadedConstraintModel!=null) {
					statisticsStringBuilder.append(System.lineSeparator());
					statisticsStringBuilder.append("#constraints: ");
					statisticsStringBuilder.append(loadedConstraintModel.getConstraints().size());
				}
				
				FeatureModelConstraintsTuple tuple = new FeatureModelConstraintsTuple(loadedFeatureModel, loadedConstraintModel);
				
				featureModelDateMap.put(tuple, fileDate.getValue());
			}
			
		}

		
		DwFeatureModelEvolutionImporter featureModelEvolutionImporter = new DwFeatureModelEvolutionImporter();
		try {
			System.out.println("Start Import!");
			long start = System.currentTimeMillis();
			
			FeatureModelConstraintsTuple mergedTuple = featureModelEvolutionImporter.importFeatureModelEvolutionSnapshots(featureModelDateMap);
			
			long end = System.currentTimeMillis();
			System.out.println("Importing evolution steps took "+(end-start)+" milliseconds.");
			
			IFile featureModelFile = dwFeatureModelWizardImportedFilePage.getModelFile();
			IFile constraintFile = ResourceUtil.deriveFile(featureModelFile, HyConstraintUtil.getConstraintModelFileExtensionForXmi());
			IFile constraintFileCS = ResourceUtil.deriveFile(featureModelFile, HyConstraintUtil.getConstraintModelFileExtensionForConcreteSyntax());
			
			System.out.println("Start saving merged model");
			
			start = System.currentTimeMillis();
			
			EcoreIOUtil.saveModelAs(mergedTuple.getFeatureModel(), featureModelFile);
			EcoreIOUtil.saveModelAs(mergedTuple.getConstraintModel(), constraintFile);
			EcoreIOUtil.saveModelAs(mergedTuple.getConstraintModel(), constraintFileCS);
			
			end = System.currentTimeMillis();
			
			System.out.println("Saving merged model took "+(end-start)+" milliseconds.");
			
			statisticsStringBuilder.append(System.lineSeparator());
			statisticsStringBuilder.append("Merged models:");
			statisticsStringBuilder.append(System.lineSeparator());
			statisticsStringBuilder.append("#features: ");
			statisticsStringBuilder.append(mergedTuple.getFeatureModel().getFeatures().size());

			statisticsStringBuilder.append(System.lineSeparator());
			statisticsStringBuilder.append("#groups: ");
			statisticsStringBuilder.append(mergedTuple.getFeatureModel().getGroups().size());
			
			if(mergedTuple.getConstraintModel()!=null) {
				statisticsStringBuilder.append(System.lineSeparator());
				statisticsStringBuilder.append("#constraints: ");
				statisticsStringBuilder.append(mergedTuple.getConstraintModel().getConstraints().size());
			}
			
			System.out.println(statisticsStringBuilder.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
    public String getWindowTitle() {
            return "DarwinSPL: Import Evolution Snapshots";
    }

	@Override
	public void addPages() {
		addPage(tfmFileSelectionWizardPage);
		addPage(featureModelEvolutionStepWizardPage);
		addPage(dwFeatureModelWizardImportedFilePage);
	}
	
	public void setSelectedFilePaths(List<String> filePaths) {
		List<IFile> files = new ArrayList<IFile>();
		
		for(String rawFilePath: filePaths) {
			files.add(ResourceUtil.makeFileRelativeToWorkspace(rawFilePath));
		}
		
		featureModelEvolutionStepWizardPage.setFiles(files);
	}
	
	@Override
	public boolean canFinish() {
		return (tfmFileSelectionWizardPage.isPageComplete() && featureModelEvolutionStepWizardPage.isPageComplete() && dwFeatureModelWizardImportedFilePage.isPageComplete());
	}

}
