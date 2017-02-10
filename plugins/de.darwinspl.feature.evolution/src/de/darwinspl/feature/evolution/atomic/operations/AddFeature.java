/**
 * 
 */
package de.darwinspl.feature.evolution.atomic.operations;

import java.util.Date;

import de.darwinspl.feature.evolution.basic.operations.ComplexOperation;
import de.darwinspl.feature.evolution.invoker.EvolutionOperation;
import eu.hyvar.feature.HyFeature;
import eu.hyvar.feature.HyFeatureFactory;
import eu.hyvar.feature.HyFeatureModel;

/**
 *
 */
public class AddFeature implements EvolutionOperation {

	private HyFeature feature;
	private Date timestamp;
	private HyFeatureModel tfm;
	
	private static final HyFeatureFactory factory = HyFeatureFactory.eINSTANCE;
	
	public AddFeature(Date timestamp, HyFeatureModel tfm) {
		
		this.timestamp = timestamp;
		this.tfm = tfm;
		
	}
	/* (non-Javadoc)
	 * @see de.darwinspl.feature.evolution.basic.operations.ComplexOperation#execute()
	 */
	@Override
	public void execute() {
		
		feature = factory.createHyFeature();

		feature.setValidSince(timestamp);
		feature.setValidUntil(null);
		
		tfm.getFeatures().add(feature);
	}

	/* (non-Javadoc)
	 * @see de.darwinspl.feature.evolution.basic.operations.ComplexOperation#undo()
	 */
	@Override
	public void undo() {
		//check if the execute method was executed, otherwise leave this method
		if (feature == null) {
			return;
		}
		tfm.getFeatures().remove(feature);
		feature = null;

	}
	public HyFeature getFeature() {
		return feature;
	}

}
