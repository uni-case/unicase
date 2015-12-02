package tr.org.unicase.authentication.web.view.internals;

import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import tr.org.unicase.authentication.web.view.internals.config.Configuration;
import tr.org.unicase.web.view.api.IViewFactory;

public class Activator implements BundleActivator {

	private static final Logger LOGGER = Logger.getLogger(Activator.class.getName());
	private ServiceRegistration<IViewFactory> viewFactoryServiceRegistration = null;

	public void start(BundleContext context) {
		LOGGER.info(Configuration.Bundle.START_MESSAGE);
		viewFactoryServiceRegistration = context.registerService(IViewFactory.class, new AuthenticationViewFactory(), null);
	}

	public void stop(BundleContext context) {
		LOGGER.info(Configuration.Bundle.STOP_MESSAGE);
		if (viewFactoryServiceRegistration != null) 
			viewFactoryServiceRegistration.unregister();
	}

}
