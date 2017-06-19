package eu.hyvar.feature.graphical.editor.commands.feature;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;

import eu.hyvar.evolution.HyTemporalElement;
import eu.hyvar.feature.HyFeature;
import eu.hyvar.feature.graphical.base.editor.DwGraphicalFeatureModelViewer;
import eu.hyvar.feature.graphical.base.editparts.HyFeatureEditPart;
import eu.hyvar.feature.graphical.base.editparts.HyRootFeatureEditPart;
import eu.hyvar.feature.graphical.base.model.HyFeatureModelWrapped;
import eu.hyvar.feature.graphical.base.model.HyFeatureWrapped;
import eu.hyvar.feature.graphical.base.model.HyParentChildConnection;
import eu.hyvar.feature.graphical.editor.commands.DwFeatureModelEditorCommand;

public class HyFeatureDeleteCommand extends DwFeatureModelEditorCommand{
	EditPart host;

	public HyFeatureDeleteCommand(DwGraphicalFeatureModelViewer viewer, EditPart host) {
		super(viewer);

		this.host = host;
	}

	private HyFeatureWrapped feature;
	private HyFeatureWrapped oldParent;
	private HyFeature oldFeature;
	private Date executionDate;

	public void setFeature(HyFeatureWrapped feature) {
		this.feature = feature;
	}

	private void restrictHyLinearTemporalElementsToParentValidUntil(EList<HyTemporalElement> elements){
		Date date = viewer.getCurrentSelectedDate();
		for(HyTemporalElement element : elements){
			if(element.getValidUntil() == null || element.getValidUntil().after(date)){
				element.setValidUntil(date);
			}
		}			
	}

	@Override
	public boolean canExecute() {
		if(host instanceof HyRootFeatureEditPart)
			return false;
		if(host instanceof HyFeatureEditPart)
			return true;
		
		return false;
	}


	@SuppressWarnings("unchecked")
	public void redo(){
		
		HyFeature feature = this.feature.getWrappedModelElement();
		Date date = viewer.getCurrentSelectedDate();
		if(date.equals(new Date(Long.MIN_VALUE)))
			date = null;
		
		executionDate = date;

		oldFeature = EcoreUtil.copy(feature);
		oldParent = this.feature.getParentFeature(date);
		
		if(date == null){
			viewer.getModelWrapped().removeFeaturePermanently(this.feature);
		}else{
			feature.setValidUntil(date);

			// restrict feature parameters to the date
			restrictHyLinearTemporalElementsToParentValidUntil((EList<HyTemporalElement>)(EList<?>)feature.getNames());
			restrictHyLinearTemporalElementsToParentValidUntil((EList<HyTemporalElement>)(EList<?>)feature.getAttributes());
			restrictHyLinearTemporalElementsToParentValidUntil((EList<HyTemporalElement>)(EList<?>)feature.getVersions());
		
			viewer.getModelWrapped().removeFeature(this.feature, date);
		}
		

		
		

		// delete the selection from the element
		host.setSelected(0);

		viewer.getModelWrapped().rearrangeFeatures();
		viewer.refreshView();
		
	}
	
	public void undo(){
		
		Date date = executionDate;
		HyParentChildConnection connection = new HyParentChildConnection();
		
		this.feature.setWrappedModelElement(oldFeature);
		HyFeatureModelWrapped featureModel = viewer.getModelWrapped();
		date = viewer.getCurrentSelectedDate();
		if(date.equals(new Date(Long.MIN_VALUE))){
			date = null;
		}		
		

		
		connection = new HyParentChildConnection();
		connection.setSource(oldParent);
		connection.setTarget(this.feature);
		connection.setModel(featureModel);
		
		featureModel.addConnection(connection, featureModel.getSelectedDate(), null);
		
		featureModel.rearrangeFeatures();
		viewer.refreshView();	
	}
	
	@Override
	public void execute(){
		redo();
	}
}