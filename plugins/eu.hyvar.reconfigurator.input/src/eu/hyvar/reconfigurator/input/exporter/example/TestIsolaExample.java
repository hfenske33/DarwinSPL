package eu.hyvar.reconfigurator.input.exporter.example;

import java.util.Date;

import eu.hyvar.reconfigurator.input.exporter.HyVarRecExporter;
import eu.hyvar.reconfigurator.input.exporter.HyVarRecExporter.FeatureEncoding;
import eu.hyvar.reconfigurator.input.exporter.example.IsolaExample.ProfileEnum;

public class TestIsolaExample {
	
	public static void main(String[] args) {
		
		// load existing model
		
		// create new Model:		
		IsolaExample isolaExample = new IsolaExample(ProfileEnum.ECO);
		
		HyVarRecExporter jsonExporter = new HyVarRecExporter(FeatureEncoding.INTEGER);
		jsonExporter.exportSPL(isolaExample.getContextModel(), isolaExample.getContextValidityModel(), isolaExample.getFeatureModel(), isolaExample.getConstraintModel(), isolaExample.getOldConfiguration(), isolaExample.getPreferenceModel(), null, new Date(), null);
	}
	
	public static void save(IsolaExample isolaExample) {
		
	}
	
}
