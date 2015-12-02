package tr.org.unicase.web.controller.api.internals;

import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import tr.org.unicase.web.controller.api.internals.config.Configuration;

public class Activator implements BundleActivator {

	private static final Logger LOGGER = Logger.getLogger(Activator.class.getName());

	public void start(BundleContext bundleContext) throws Exception {
		LOGGER.info(Configuration.BUNDLE.MESSAGE_START);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		LOGGER.info(Configuration.BUNDLE.MESSAGE_STOP);
	}

}
