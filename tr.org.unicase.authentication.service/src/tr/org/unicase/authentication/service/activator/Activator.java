package tr.org.unicase.authentication.service.activator;

import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import tr.org.unicase.authentication.service.UserService;
import tr.org.unicase.authentication.service.internals.config.Configuration;

public class Activator implements BundleActivator {

	private static final Logger LOGGER = Logger.getLogger(Activator.class.getName());
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		LOGGER.info(Configuration.Bundle.START_MESSAGE);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		LOGGER.info(Configuration.Bundle.STOP_MESSAGE);
	}

	public static UserService getUserService() {
		ServiceReference<UserService> reference = Activator.context.getServiceReference(UserService.class);
		if (reference != null)
			return Activator.context.getService(reference);
		return null;
	}

	public static PasswordEncoder getPasswordEncoder() {
		ServiceReference<PasswordEncoder> reference = Activator.context.getServiceReference(PasswordEncoder.class);
		if (reference != null)
			return Activator.context.getService(reference);
		return null;
	}
}
