package tr.org.unicase.reference.web.view.internals;

import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import tr.org.unicase.reference.web.view.internals.config.Configuration;
import tr.org.unicase.web.view.api.IViewFactory;

public class Activator implements BundleActivator {

	private static final Logger LOGGER = Logger.getLogger(Activator.class.getName());
	private static BundleContext context = null;

	private ServiceRegistration<IViewFactory> viewFactoryServiceRegistration;

	@Override
	public void start(BundleContext context) throws Exception {
		LOGGER.info(Configuration.Bundle.START_MESSAGE);
		Activator.context = context;
		registerReferenceViewFactory();
	}

	private void registerReferenceViewFactory() {
		viewFactoryServiceRegistration = context.registerService(IViewFactory.class, new ReferenceViewFactory(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		LOGGER.info(Configuration.Bundle.STOP_MESSAGE);
				
		if (viewFactoryServiceRegistration != null) {
			viewFactoryServiceRegistration.unregister();
			viewFactoryServiceRegistration = null;
		}
		
		Activator.context = null;
	}

	public static BundleContext getContext() {
		return Activator.context;
	}

}
