package de.darwinspl.feature.graphical.editor.commands.group;

import java.util.Date;

import de.darwinspl.feature.graphical.base.editor.DwGraphicalFeatureModelViewer;
import de.darwinspl.feature.graphical.editor.commands.DwLinearTemporalElementCommand;
import de.darwinspl.feature.graphical.editor.util.DwEcoreUtil;
import de.darwinspl.feature.graphical.editor.util.DwElementEditorUtil;
import eu.hyvar.evolution.util.HyEvolutionUtil;
import eu.hyvar.feature.HyFeatureFactory;
import eu.hyvar.feature.HyGroup;
import eu.hyvar.feature.HyGroupComposition;
import eu.hyvar.feature.HyGroupType;
import eu.hyvar.feature.HyGroupTypeEnum;

public class DwGroupChangeGroupTypeCommand extends DwLinearTemporalElementCommand{
	private HyGroup group;
	private HyGroupTypeEnum newGroupTypeEnum;
	private DwGraphicalFeatureModelViewer editor;
	
	HyGroupType newType;
	HyGroupType oldType;
	HyGroupType changedType;
	
	HyGroup copy;
	
	Date executionDate;
	
	public DwGroupChangeGroupTypeCommand(HyGroup group, HyGroupTypeEnum newGroupTypeEnum, DwGraphicalFeatureModelViewer editor){
		this.group = group;
		this.newGroupTypeEnum = newGroupTypeEnum;
		this.editor = editor;
	}
	
	@Override
	public void execute(){
		redo();
	}
	
	@Override
	public boolean canExecute() {
		return editor.isLastDateSelected();
	}
	
	/**
	 * TODO undo not working properly. Example case: the group type was changed at since == Long.MIN_VALUE then the preccessor type
	 * was deleted due to invalid date(since == until == null/Long.MIN_VALUE). Therefore the old type has to be added again to the
	 * group 
	 */
	@Override
	public void undo() {	
		removeElementFromLinkedList(newType);
		
		// if the group type was changed at the first date, the old type is deleted. To overcome the missing type, the deleted group type, saved
		// in a local variable, is added again to the group lists.
		if(executionDate.equals(new Date(Long.MIN_VALUE))) {
			group.getTypes().add(oldType);
		}
		
		if(oldType.getSupersedingElement() == null && changedType.getSupersedingElement() != null) {
			group.getTypes().remove(changedType.getSupersedingElement());
			changedType.setSupersedingElement(null);
		}
		group.getTypes().remove(newType);
	}

	@Override
	public void redo() {
		executionDate = editor.getCurrentSelectedDate();
		
		HyGroupType type = HyEvolutionUtil.getValidTemporalElement(group.getTypes(), executionDate);
		
		changedType = type;
		oldType = DwEcoreUtil.copy(type);
		newType = HyFeatureFactory.eINSTANCE.createHyGroupType();
		newType.setType(newGroupTypeEnum);
		
		HyGroupComposition composition = HyEvolutionUtil.getValidTemporalElement(group.getParentOf(), executionDate);
		if(composition.getSupersedingElement() != null) {
			HyGroupComposition supersedingComposition = ((HyGroupComposition)composition.getSupersedingElement());
			if(supersedingComposition.getFeatures().size() == 1 && newGroupTypeEnum != HyGroupTypeEnum.AND) {
				HyGroupType supersedingType = HyFeatureFactory.eINSTANCE.createHyGroupType();
				supersedingType.setType(HyGroupTypeEnum.AND);
				supersedingType.setValidSince(supersedingComposition.getValidSince());
				group.getTypes().add(supersedingType);
				
				type.setValidUntil(supersedingComposition.getValidSince());
				
				if(type.getSupersedingElement() == null) {					
					type.setSupersedingElement(supersedingType);
				}else {
					supersedingType.setValidUntil(type.getSupersedingElement().getValidSince());
					type.getSupersedingElement().setSupersededElement(supersedingType);
					type.setSupersedingElement(supersedingType);
				}
			}
		}
		
		
		group.getTypes().add(newType);
		if(executionDate.equals(new Date(Long.MIN_VALUE))) {
			group.getTypes().remove(type);
		}else{
			changeVisibilities(type, newType, executionDate);
		}
		
		DwElementEditorUtil.cleanGroupTypes(group);
	}
}
