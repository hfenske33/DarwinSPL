package eu.hyvar.feature.graphical.configurator.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

import eu.hyvar.feature.configuration.HyConfiguration;
import eu.hyvar.feature.graphical.base.editor.DwGraphicalFeatureModelViewer;
import eu.hyvar.feature.graphical.base.editparts.HyFeatureEditPart;
import eu.hyvar.feature.graphical.base.model.HyFeatureModelWrapped;
import eu.hyvar.feature.graphical.base.model.HyFeatureWrapped;
import eu.hyvar.feature.graphical.configurator.editor.HyFeatureModelConfiguratorEditor;
import eu.hyvar.feature.graphical.configurator.figures.HyConfiguratorFeatureFigure;
import eu.hyvar.feature.graphical.configurator.predicates.HyConfiguratorElementHasFeaturePredicate;
import eu.hyvar.feature.graphical.configurator.util.HyConfiguratorEditorUtil;

public class HyConfiguratorEditorFeatureEditPart extends HyFeatureEditPart{

	public HyConfiguratorEditorFeatureEditPart(DwGraphicalFeatureModelViewer editor, HyFeatureModelWrapped featureModel) {
		super(editor, featureModel);
	}
	
	@Override
	protected IFigure createFigure() {	
		return new HyConfiguratorFeatureFigure(editor, (HyFeatureWrapped)getModel());
	}


	@Override
	public void performRequest(Request request) {
		//React to double click
		if (request.getType() == RequestConstants.REQ_OPEN) {
			HyFeatureWrapped feature = (HyFeatureWrapped)getModel();

			HyFeatureModelConfiguratorEditor editor = (HyFeatureModelConfiguratorEditor)getEditor();
			HyConfiguration configuration = editor.getSelectedConfiguration();
			
			HyConfiguratorElementHasFeaturePredicate<Object> predicate = new HyConfiguratorElementHasFeaturePredicate<>();
			predicate.feature = feature.getWrappedModelElement();
			
			if(!configuration.getElements().removeIf(predicate)){
				HyConfiguratorEditorUtil.addFeatureToConfiguration(configuration, feature.getWrappedModelElement(), editor.getCurrentSelectedDate());
			}else{
				HyConfiguratorEditorUtil.removeChildrenFeaturesFromConfiguration(configuration, feature.getWrappedModelElement(), editor.getCurrentSelectedDate());
			}
		}
		
		editor.refreshView();
	}	
}