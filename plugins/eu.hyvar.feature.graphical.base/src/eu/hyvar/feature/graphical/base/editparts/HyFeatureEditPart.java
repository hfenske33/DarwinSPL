package eu.hyvar.feature.graphical.base.editparts;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;

import eu.hyvar.feature.HyFeature;
import eu.hyvar.feature.HyFeatureAttribute;
import eu.hyvar.feature.HyVersion;
import eu.hyvar.feature.graphical.base.deltaecore.wrapper.HyGeometryUtil;
import eu.hyvar.feature.graphical.base.deltaecore.wrapper.layouter.version.HyVersionLayouterManager;
import eu.hyvar.feature.graphical.base.editor.DwGraphicalFeatureModelViewer;
import eu.hyvar.feature.graphical.base.figures.HyFeatureFigure;
import eu.hyvar.feature.graphical.base.model.HyFeatureModelWrapped;
import eu.hyvar.feature.graphical.base.model.HyFeatureWrapped;
import eu.hyvar.feature.graphical.base.model.HyParentChildConnection;

public class HyFeatureEditPart extends HyAbstractEditPart implements NodeEditPart{
	private HyFeatureAdapter adapter;
	
	public class HyFeatureAdapter implements Adapter {

		@Override 
		public void notifyChanged(Notification notification) {
			
			if(notification.getEventType() == ENotificationImpl.REMOVE && notification.getNotifier() instanceof HyFeature){
				// in case that the feature was deleted do nothing
			}else{
				refreshChildren();
				refreshVisuals();
			}
		}

		@Override 
		public Notifier getTarget() {
			return (HyFeature)((HyFeatureWrapped)getModel()).getWrappedModelElement();
		}

		@Override public void setTarget(Notifier newTarget) {
			// Do nothing.
		}

		@Override public boolean isAdapterForType(Object type) {
			return type.equals(HyFeature.class);
		}
	} 

	public HyFeatureEditPart(DwGraphicalFeatureModelViewer editor, HyFeatureModelWrapped featureModel){
		super(editor, featureModel);

		adapter = new HyFeatureAdapter();
	}

	@Override
	protected IFigure createFigure() {	
		return new HyFeatureFigure(editor, (HyFeatureWrapped)getModel());
	}

	@Override 
	public void activate() {
		if(!isActive()) {
			HyFeatureWrapped model = ((HyFeatureWrapped)getModel());
			model.getWrappedModelElement().eAdapters().add(adapter);
		}
		
		super.activate();
	}

	@Override 
	public void deactivate() {
		if(isActive()) {
			HyFeatureWrapped model = ((HyFeatureWrapped)getModel());

			model.getWrappedModelElement().eAdapters().remove(adapter);
		}
		super.deactivate();
	}



	@Override
	public void propertyChange(PropertyChangeEvent evt) {		   
		refreshVisuals();

		refreshTargetConnections();
		refreshSourceConnections();	
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return ((HyFeatureFigure)getFigure()).getChildrenAnchor();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return ((HyFeatureFigure)getFigure()).getParentAnchor();	
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return ((HyFeatureFigure)getFigure()).getChildrenAnchor();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return ((HyFeatureFigure)getFigure()).getParentAnchor();
	}

	/**
	 * Fetch all versions related to the feature to display them as subfeatures
	 */
	@Override 
	protected ArrayList<EObject> getModelChildren() {
		HyFeatureWrapped model = (HyFeatureWrapped) getModel();	

		ArrayList<EObject> objects = new ArrayList<>();
		for(HyVersion version : model.getWrappedModelElement().getVersions()){
			objects.add(version);
		}
		
		for(HyFeatureAttribute attribute : model.getWrappedModelElement().getAttributes()){
			objects.add(attribute);
		}
		
		return objects;	
	}
	
	
	
	protected boolean hasModifier(HyFeatureWrapped feature){
		Date date = ((DwGraphicalFeatureModelViewer)this.editor).getCurrentSelectedDate();
		if(date == null)
			date = new Date();

		return feature.isWithoutModifier(date);
	}


	@Override
	public void refreshVisuals(){
		refreshChildren();
		super.refreshVisuals();
		
		DwGraphicalFeatureModelViewer editor = (DwGraphicalFeatureModelViewer)this.editor;
		Date date = editor.getCurrentSelectedDate();

		HyFeatureFigure figure = (HyFeatureFigure)getFigure();
		HyFeatureWrapped wrappedFeature = (HyFeatureWrapped)this.getModel();

		boolean featureIsCurrentlyValid = wrappedFeature.isValid(date);

		if(featureIsCurrentlyValid){
			figure.update();
		}
		
		figure.setVisible(wrappedFeature.isVisible());
			
		
		for(HyParentChildConnection connection : this.getModelSourceConnections()){
			connection.notifyChange();
		}
		
		refreshVisualsOfChildren();
	}
	
	
	@Override
	protected void refreshChildren() {
		super.refreshChildren();
		
		HyFeature feature = ((HyFeatureWrapped)getModel()).getWrappedModelElement();
		
		DwGraphicalFeatureModelViewer editor = (DwGraphicalFeatureModelViewer)this.editor;
		Date date = editor.getCurrentSelectedDate();
		HyVersionLayouterManager.updateLayouter(feature, date);
	}
	
	@Override
	protected void createEditPolicies(){
	}


	@Override 
	protected List<HyParentChildConnection> getModelSourceConnections() {
		HyFeatureWrapped model = (HyFeatureWrapped)getModel();

		DwGraphicalFeatureModelViewer editor = (DwGraphicalFeatureModelViewer)this.editor;
		Date date = editor.getCurrentSelectedDate();
		return model.getChildrenConnections(date);
	}

	@Override 
	protected List<HyParentChildConnection> getModelTargetConnections() {
		HyFeatureWrapped model = (HyFeatureWrapped)getModel();

		DwGraphicalFeatureModelViewer editor = (DwGraphicalFeatureModelViewer)this.editor;
		Date date = editor.getCurrentSelectedDate();
		return model.getParentConnections(date);
	}

	/**
	 * Refresh the visual representation of all versions and attributes related to this feature
	 */
	protected void refreshVisualsOfChildren(){
		
		for(Object o : this.getChildren()){
			if(o instanceof HyVersionEditPart){
				HyVersionEditPart edit = (HyVersionEditPart)o;
				edit.refreshVisuals();
			}

			if(o instanceof HyAttributeEditPart){
				HyAttributeEditPart edit = (HyAttributeEditPart)o;
				edit.refreshVisuals();
			}
		}		
	}
	
	@Override
	protected Rectangle getFigureConstraint() {
		HyFeatureWrapped feature = (HyFeatureWrapped)getModel();
		DwGraphicalFeatureModelViewer editor = (DwGraphicalFeatureModelViewer)this.editor;
		Date date = editor.getCurrentSelectedDate();
		
		int width = HyGeometryUtil.calculateFeatureWidth(feature.getWrappedModelElement(), date);
		int height = feature.getSize(date).height; 
		
		
		Dimension newFeatureSize = new Dimension(width, height);
		feature.setSize(newFeatureSize);

		Rectangle layout = new Rectangle(feature.getPosition(date).getPosition(), newFeatureSize);
		return layout;
	}
}