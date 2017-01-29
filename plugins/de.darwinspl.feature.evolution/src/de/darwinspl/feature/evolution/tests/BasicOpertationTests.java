/**
 * 
 */
package de.darwinspl.feature.evolution.tests;

import de.darwinspl.feature.evolution.basic.operations.AddFeatureInGroup;
import de.darwinspl.feature.evolution.basic.operations.AddFeatureWithGroup;
import de.darwinspl.feature.evolution.basic.operations.ChangeFeatureType;
import de.darwinspl.feature.evolution.basic.operations.ChangeGroupType;
import de.darwinspl.feature.evolution.basic.operations.DeleteFeatureInGroup;
import de.darwinspl.feature.evolution.basic.operations.DeleteFeatureWithGroup;
import de.darwinspl.feature.evolution.basic.operations.MoveFeature;
import de.darwinspl.feature.evolution.basic.operations.MoveFeatureAddGroup;
import de.darwinspl.feature.evolution.basic.operations.MoveFeatureDeleteAndAddGroup;
import de.darwinspl.feature.evolution.basic.operations.MoveFeatureDeleteGroup;
import de.darwinspl.feature.evolution.basic.operations.MoveGroup;
import de.darwinspl.feature.evolution.basic.operations.Rename;
import eu.hyvar.feature.HyFeatureModel;
import eu.hyvar.feature.HyFeatureTypeEnum;
import eu.hyvar.feature.HyGroupTypeEnum;

/**
 *
 */
public class BasicOpertationTests extends TestCases {

	public static void basicOperationAddTest(HyFeatureModel tfm) {
		AddFeatureInGroup addFiG = new AddFeatureInGroup("test2", HyFeatureTypeEnum.MANDATORY, frontDistanceSensorsAlternativeGroup,
				timestamp, tfm);
		addFiG.execute();

		AddFeatureWithGroup addFwG = new AddFeatureWithGroup("test3", HyFeatureTypeEnum.MANDATORY,
				infotainmentFeature, timestamp, tfm);
		addFwG.execute();
	}
	
	public static void basicOperationDeleteTest(HyFeatureModel tfm) {
		
		calendar.set(2016, 0, 20, 23, 59, 59);
		timestamp = calendar.getTime();
		
		DeleteFeatureInGroup deleteFiG = new DeleteFeatureInGroup(infotainmentFeature, timestamp);
		deleteFiG.execute();
		
		calendar.set(2015, 0, 20, 23, 59, 59);
		timestamp = calendar.getTime();
		AddFeatureWithGroup addFwG = new AddFeatureWithGroup("test3", HyFeatureTypeEnum.MANDATORY,
				infotainmentFeature, timestamp, tfm);
		addFwG.execute();
		
		calendar.set(2016, 0, 20, 23, 59, 59);
		timestamp = calendar.getTime();
		DeleteFeatureWithGroup deleteFwG = new DeleteFeatureWithGroup(addFwG.getFeature(), addFwG.getGroup(), timestamp);
		deleteFwG.execute();
	}
	
	public static void basicOperationRenameTest(HyFeatureModel tfm) {
		
		calendar.set(2016, 0, 20, 23, 59, 59);
		timestamp = calendar.getTime();
		
		Rename rename = new Rename(infotainmentFeature, "Test 123", timestamp);
		rename.execute();
	}
	
	public static void basicOperationChangeTypeTest(HyFeatureModel tfm) {
		
		calendar.set(2016, 3, 20, 23, 59, 59);
		timestamp = calendar.getTime();
		
		ChangeFeatureType changeTypeF = new ChangeFeatureType(infotainmentFeature, HyFeatureTypeEnum.OPTIONAL, timestamp);
		changeTypeF.execute();
		
		ChangeGroupType changeTypeG = new ChangeGroupType(frontDistanceSensorsAlternativeGroup, HyGroupTypeEnum.OR, timestamp);
		changeTypeG.execute();
	}
	
	public static void basicOperationMoveTest(HyFeatureModel tfm) {
		
		calendar.set(2016, 0, 20, 23, 59, 59);
		timestamp = calendar.getTime();
		
		MoveFeature moveFeature = new MoveFeature(infotainmentFeature, frontDistanceSensorsAlternativeGroup.getParentOf().get(0), timestamp);
		moveFeature.execute();
		
		calendar.set(2016, 1, 20, 23, 59, 59);
		timestamp = calendar.getTime();
		
		MoveFeatureAddGroup moveFeatureAddGroup = new MoveFeatureAddGroup(infotainmentFeature, frontDistanceSensorsFeature, timestamp, tfm);
		moveFeatureAddGroup.execute();
		
		calendar.set(2016, 2, 20, 23, 59, 59);
		timestamp = calendar.getTime();
		
		MoveFeatureDeleteAndAddGroup moveFeatureDeleteAdd = new MoveFeatureDeleteAndAddGroup(infotainmentFeature, moveFeatureAddGroup.getGroup(), sideFeature, timestamp, tfm);
		moveFeatureDeleteAdd.execute();
		
		calendar.set(2016, 3, 20, 23, 59, 59);
		timestamp = calendar.getTime();
		
		MoveFeatureDeleteGroup movefeatureDeleteGroup = new MoveFeatureDeleteGroup(infotainmentFeature, moveFeatureDeleteAdd.getNewGroup(), frontDistanceSensorsAlternativeGroup.getParentOf().get(0), timestamp);
		movefeatureDeleteGroup.execute();
		
		calendar.set(2016, 4, 20, 23, 59, 59);
		timestamp = calendar.getTime();
		
		MoveGroup moveGroup = new MoveGroup(frontDistanceSensorsAlternativeGroup, carFeature, timestamp);
		moveGroup.execute();
		
	}
}
