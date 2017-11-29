/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package de.darwinspl.preferences.resource.dwprofile.analysis;

import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class HydatavalueINTEGER_LITERALTokenResolver implements de.darwinspl.preferences.resource.dwprofile.IDwprofileTokenResolver {
	
	private eu.hyvar.dataValues.resource.hydatavalue.analysis.HydatavalueINTEGER_LITERALTokenResolver importedResolver = new eu.hyvar.dataValues.resource.hydatavalue.analysis.HydatavalueINTEGER_LITERALTokenResolver();
	
	public String deResolve(Object value, EStructuralFeature feature, EObject container) {
		String result = importedResolver.deResolve(value, feature, container);
		return result;
	}
	
	public void resolve(String lexem, EStructuralFeature feature, final de.darwinspl.preferences.resource.dwprofile.IDwprofileTokenResolveResult result) {
		importedResolver.resolve(lexem, feature, new eu.hyvar.dataValues.resource.hydatavalue.IHydatavalueTokenResolveResult() {
			public String getErrorMessage() {
				return result.getErrorMessage();
			}
			
			public Object getResolvedToken() {
				return result.getResolvedToken();
			}
			
			public void setErrorMessage(String message) {
				result.setErrorMessage(message);
			}
			
			public void setResolvedToken(Object resolvedToken) {
				result.setResolvedToken(resolvedToken);
			}
			
		});
	}
	
	public void setOptions(Map<?,?> options) {
		importedResolver.setOptions(options);
	}
	
}