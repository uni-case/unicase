package tr.org.unicase.reference.service.internals;

import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import tr.org.unicase.reference.service.internals.config.Configuration;

public class Activator implements BundleActivator {

	private static final Logger LOGGER = Logger.getLogger(Activator.class.getName());

	@Override
	public void start(BundleContext context) throws Exception {
		LOGGER.info(Configuration.Bundle.MESSAGE_START);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		LOGGER.info(Configuration.Bundle.MESSAGE_STOP);
	}

}
