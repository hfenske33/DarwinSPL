/**
 *
 * $Id$
 */
package de.darwinspl.anomaly.validation;

import eu.hyvar.feature.HyFeature;

/**
 * A sample validator interface for {@link de.darwinspl.anomaly.DwFeatureAnomaly}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface DwFeatureAnomalyValidator {
	boolean validate();

	boolean validateFeature(HyFeature value);
}