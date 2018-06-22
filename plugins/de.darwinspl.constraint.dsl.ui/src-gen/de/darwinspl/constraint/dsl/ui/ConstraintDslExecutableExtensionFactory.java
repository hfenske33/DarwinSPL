/*
 * generated by Xtext 2.12.0
 */
package de.darwinspl.constraint.dsl.ui;

import com.google.inject.Injector;
import de.darwinspl.constraint.dsl.ui.internal.DslActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class ConstraintDslExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return DslActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return DslActivator.getInstance().getInjector(DslActivator.DE_DARWINSPL_CONSTRAINT_DSL_CONSTRAINTDSL);
	}
	
}