package eu.hyvar.feature.graphical.editor.commands.version;

import java.util.Date;

import org.eclipse.gef.commands.Command;

import eu.hyvar.feature.HyVersion;
import eu.hyvar.feature.graphical.editor.editor.HyGraphicalFeatureModelEditor;

public class HyVersionEvolutionDeleteCommand extends Command{
	private HyGraphicalFeatureModelEditor editor;
	private HyVersion version;
	
	public HyGraphicalFeatureModelEditor getEditor() {
		return editor;
	}

	public void setEditor(HyGraphicalFeatureModelEditor editor) {
		this.editor = editor;
	}
		
	public HyVersion getVersion() {
		return version;
	}

	public void setVersion(HyVersion version) {
		this.version = version;
	}

	public HyVersionEvolutionDeleteCommand(HyGraphicalFeatureModelEditor editor) {
		this.editor = editor;
	}
	
	private void deleteVersionAndChildrenTemporarily(HyVersion version){
		Date date = editor.getCurrentSelectedDate();
		
		version.setValidUntil(date);
		for(HyVersion child : version.getSupersedingVersions()){
			child.setValidUntil(date);
			
			deleteVersionAndChildrenTemporarily(child);
		}
	}

	
	@Override
	public void execute(){
		deleteVersionAndChildrenTemporarily(version);
		
		editor.getModelWrapped().getWrappedFeature(version.getFeature()).getListeners().firePropertyChange("versions", 0, 1);
		
		editor.getModelWrapped().rearrangeFeatures();
		editor.refreshView();
	}
}