package de.darwinspl.anomalies.explanations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import de.darwinspl.feature.evolution.editoroperation.DwEditorOperation;
import de.darwinspl.feature.evolution.editoroperation.EditorOperationExplanation;
import eu.hyvar.context.contextValidity.HyValidityFormula;
import eu.hyvar.evolution.util.HyEvolutionUtil;
import eu.hyvar.feature.HyFeature;
import eu.hyvar.feature.HyGroup;
import eu.hyvar.feature.HyGroupComposition;
import eu.hyvar.feature.HyRootFeature;
import eu.hyvar.feature.constraint.HyConstraint;
import eu.hyvar.feature.util.HyFeatureEvolutionUtil;

public class AnomalyConstraintExplanation {
	
	private EObject objReference; // TODO: rename properly
	private String stringReference; // TODO: rename properly
	private List<EditorOperationExplanation> editorOperationExplanations = new ArrayList<EditorOperationExplanation>();
	private Date date;
	
	public String explain() {
		String constraintStringExplanation = "";
		if (objReference instanceof HyRootFeature) {
			HyRootFeature rootFeature = (HyRootFeature) objReference;
			constraintStringExplanation = explain(rootFeature);
		} else if (objReference instanceof HyFeature) {
			HyFeature feature = (HyFeature) objReference;
			constraintStringExplanation = explain(feature);
		} else if (objReference instanceof HyConstraint) {
			HyConstraint constraint = (HyConstraint) objReference;
			constraintStringExplanation = explain(constraint);
		} else if (objReference instanceof HyValidityFormula) {
			HyValidityFormula validityFormula = (HyValidityFormula) objReference;
			constraintStringExplanation = explain(validityFormula);
		} else if (objReference instanceof HyGroup) {
			HyGroup group = (HyGroup) objReference;
			constraintStringExplanation = explain(group);			
		} else if (objReference instanceof HyGroupComposition) {
			HyGroupComposition groupComposition = (HyGroupComposition) objReference;
			constraintStringExplanation = explain(groupComposition);
		}

		String editorOperationExplanation = "";
		if (editorOperationExplanations.size() > 0) {
			for (EditorOperationExplanation opExplanation : editorOperationExplanations) {
				if (!editorOperationExplanation.isEmpty()) {
					editorOperationExplanation += "\n";
				}
				editorOperationExplanation += opExplanation.explain();
			}
			editorOperationExplanation = "\n>Editor Operations:\n" + editorOperationExplanation;
		}
		return stringReference + " -> " + constraintStringExplanation + editorOperationExplanation;
	}
	

	/*
	 * Possible objReferences:
	 * feature: mandatory (aka parent=1 -> feature=1) // special case???
	 * constraint: constraint
	 * group: grouptype condition (parent=1 -> f1 alt/and/or f2 ... = 1)
	 * validityformula: validityformula
	 * groupcomposition: parent connection (f1 or f2 ... -> parent)
	 * rootfeature: rootfeature = 1
	 * 
	 * what's missing: contradicting translation of "anomalyfeature = 1/0"
	 * 
	 */
	
	
	private String explain(HyRootFeature rootFeature) {
		String rootFeatureName = HyEvolutionUtil.getValidTemporalElement(rootFeature.getFeature().getNames(), date).getName();
		return rootFeatureName + " is root feature.";
	}
	
	private String explain(HyFeature feature) {
		String featureName = HyEvolutionUtil.getValidTemporalElement(feature.getNames(), date).getName();
		return featureName + " is mandatory.";
	}
	
	private String explain(HyConstraint constraint) {
		return "constraint";
	}
	
	private String explain(HyValidityFormula validityFormula) {
		return "validity formula";
	}
	
	private String explain(HyGroup group) {
		String featurenames = "";
		List<HyFeature> featureList = HyFeatureEvolutionUtil.getChildsOfGroup(group, date);
		for (HyFeature feature : featureList) {
			if (!featurenames.isEmpty()) {
				featurenames += ", ";
			}
			featurenames += HyFeatureEvolutionUtil.getName(feature.getNames(), date).getName();
		}
		
		HyFeature parentFeature = HyFeatureEvolutionUtil.getParentOfGroup(group, date);
		String parentName = HyFeatureEvolutionUtil.getName(parentFeature.getNames(), date).getName();
		
		String groupType = HyFeatureEvolutionUtil.getType(group, date).getType().getName();
		
		String beWord = (featureList.size() > 1) ? "are" : "is";
		return featurenames + " " + beWord + " in an " + groupType + "-group under " + parentName;
	}
	
	private String explain(HyGroupComposition groupComposition) {
		// use explain(HyGroup) as this is just another group explanation, but in a different occasion (hence the different object)
		return explain(groupComposition.getCompositionOf());
	}

	public EObject getObjReference() {
		return objReference;
	}

	public void setObjReference(EObject objReference) {
		this.objReference = objReference;
	}

	public String getStringReference() {
		return stringReference;
	}

	public void setStringReference(String stringReference) {
		this.stringReference = stringReference;
	}

	public List<EditorOperationExplanation> getEditorOperationExplanations() {
		return editorOperationExplanations;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
}