/**
 * 
 */
package de.darwinspl.feature.evolution.tests;

import de.darwinspl.feature.evolution.atomic.operations.AddFeature;
import de.darwinspl.feature.evolution.basic.operations.AddFeatureWithGroup;
import de.darwinspl.feature.evolution.complex.operations.AddToGroupComposition;
import de.darwinspl.feature.evolution.complex.operations.RemoveFromGroupComposition;
import eu.hyvar.feature.HyFeatureModel;
import eu.hyvar.feature.HyFeatureTypeEnum;

/**
 *
 */
public class ComplexOperationsTests extends TestCases{

	public static void testTimestampAdjustment(HyFeatureModel tfm) {
		
		calendar.set(2016, 0, 20, 23, 59, 59);
		timestamp = calendar.getTime();
		
		AddFeatureWithGroup addFwG = new AddFeatureWithGroup("test3", HyFeatureTypeEnum.MANDATORY,
				infotainmentFeature, timestamp, tfm);
		addFwG.execute();
		
		AddFeature addF = new AddFeature("test1", HyFeatureTypeEnum.OPTIONAL, timestamp);
		addF.execute();
		
		//Add new Feature to the group from the basic op add(wG)
		AddToGroupComposition addGC = new AddToGroupComposition(
				addFwG.getGroup().getParentOf().get(0), addF.getFeature() , timestamp);
		addGC.execute();
		
		//change timestamp
		calendar.set(2016, 1, 31, 23, 59, 59);
		timestamp = calendar.getTime();
		
		//remove the Feature from the add(wG) operation from his group
		RemoveFromGroupComposition remGC = new RemoveFromGroupComposition(
				addGC.getNewGroupComposition(), addFwG.getFeature(), timestamp);
		remGC.execute();
		
		//change timestamp
		calendar.set(2016, 2, 31, 23, 59, 59);
		timestamp = calendar.getTime();
		
		//add the feature again to his initially group
		AddToGroupComposition addGC2 = new AddToGroupComposition(
				remGC.getNewGroupComposition(), addFwG.getFeature(), timestamp);
		addGC2.execute();
	}
	
	public static void complexOperationUndoTests(HyFeatureModel tfm) {
		
		calendar.set(2016, 0, 20, 23, 59, 59);
		timestamp = calendar.getTime();

		AddToGroupComposition addGC = new AddToGroupComposition(frontDistanceSensorsAlternativeGroup.getParentOf().get(0), sideFeature, timestamp);
		addGC.undo();
		addGC.execute();
		addGC.undo();
		
		RemoveFromGroupComposition rmGC = new RemoveFromGroupComposition(frontDistanceSensorsAlternativeGroup.getParentOf().get(0), sideFeature, timestamp);
		rmGC.undo();
		rmGC.execute();
		rmGC.undo();
		
	}
}
