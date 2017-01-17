package de.darwinspl.feature.evolution.generator.operations;

import java.util.Date;

import org.eclipse.emf.ecore.util.EcoreUtil;

import eu.hyvar.evolution.HyEvolutionUtil;
import eu.hyvar.evolution.HyLinearTemporalElement;
import eu.hyvar.feature.HyGroup;
import eu.hyvar.feature.HyGroupType;
import eu.hyvar.feature.HyGroupTypeEnum;
import eu.hyvar.feature.impl.custom.HyFeatureFactoryWithIds;

public class ChangeGroupType implements EvolutionOperation {

	protected HyGroup group;
	protected Date date;
	protected HyGroupTypeEnum oldType;
	protected HyGroupTypeEnum newType;

	public ChangeGroupType(HyGroup group, HyGroupTypeEnum newType, Date date) {
		this.group = group;
		this.date = date;
		this.oldType = HyEvolutionUtil.getValidTemporalElement(group.getTypes(), date).getType();
		this.newType = newType;
	}

	@Override
	public void applyOperation() throws EvolutionOperationException {
		if(newType.equals(oldType)) {
			return;
		}
		
		HyGroupType newGroupType = HyFeatureFactoryWithIds.eINSTANCE.createHyGroupType();
		newGroupType.setValidSince((Date)date.clone());
		newGroupType.setType(newType);

		HyGroupType oldGroupType = HyEvolutionUtil.getValidTemporalElement(group.getTypes(), date);
		oldGroupType.setValidUntil((Date)date.clone());
		group.getTypes().add(newGroupType);

//		newGroupType.setSupersedingElement(oldGroupType.getSupersedingElement());
		
		// Check if old group composition can be deleted
		if (oldGroupType.getValidSince() != null && oldGroupType.getValidSince().equals(oldGroupType.getValidUntil())) {
			HyLinearTemporalElement oldSupersededElement = oldGroupType.getSupersededElement();
			EcoreUtil.delete(oldGroupType);
			newGroupType.setSupersededElement(oldSupersededElement);
		} else {
			newGroupType.setSupersededElement(oldGroupType);
		}
	}

}