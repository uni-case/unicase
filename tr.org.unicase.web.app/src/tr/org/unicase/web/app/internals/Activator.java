package tr.org.unicase.web.app.internals;

import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import tr.org.unicase.web.app.activator.internals.HttpServiceTrackerCustomizer;
import tr.org.unicase.web.app.internals.config.Configuration;
import tr.org.unicase.web.app.internals.tracker.AbstractService;
import tr.org.unicase.web.app.internals.tracker.UnicaseAuthenticationManagerService;
import tr.org.unicase.web.app.internals.tracker.UnicaseLogManagerService;
import tr.org.unicase.web.app.internals.tracker.UnicaseViewManagerService;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static final Logger LOGGER = Logger.getLogger(Activator.class.getName());

	private ServiceTracker<HttpService, HttpService> httpServiceTracker;
	private HttpServiceTrackerCustomizer httpServiceTrackerCustomizer;

	private AbstractService<?, ?> unicaseAuthenticationService;
	private AbstractService<?, ?> unicaseLogService;
	private AbstractService<?, ?> unicaseViewService;

	private void trackHttpService() {
		httpServiceTrackerCustomizer = new HttpServiceTrackerCustomizer(context);
		httpServiceTracker = new ServiceTracker<>(context, HttpService.class, httpServiceTrackerCustomizer);
		httpServiceTracker.open();
	}

	private void untrackHttpService() {
		httpServiceTrackerCustomizer.reset();
		httpServiceTracker.close();
		httpServiceTracker = null;
		httpServiceTrackerCustomizer = null;
	}

	private void trackAuthenticationService() {
		unicaseAuthenticationService = new UnicaseAuthenticationManagerService(Activator.context);
		unicaseAuthenticationService.track();
		LOGGER.info(Configuration.AuthenticationManager.LISTENING);
	}

	private void untrackAuthenticationService() {
		unicaseAuthenticationService.untrack();
		unicaseAuthenticationService = null;
	}

	private void trackLogService() {
		unicaseLogService = new UnicaseLogManagerService(Activator.context);
		unicaseLogService.track();
		LOGGER.info(Configuration.LogManager.LISTENING);
	}

	private void untrackLogService() {
		unicaseLogService.untrack();
		unicaseLogService = null;
	}
	
	private void trackViewService() {
		unicaseViewService = new UnicaseViewManagerService(Activator.context);
		unicaseViewService.track();
		LOGGER.info(Configuration.ViewManager.LISTENING);
	}

	private void untrackViewService() {
		unicaseViewService.untrack();
		unicaseViewService = null;
	}
	
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		LOGGER.info(Configuration.Bundle.START_MESSAGE);

		trackHttpService();
		trackAuthenticationService();
		trackViewService();
		trackLogService();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		LOGGER.info(Configuration.Bundle.STOP_MESSAGE);

		untrackHttpService();
		untrackAuthenticationService();
		untrackLogService();
		untrackViewService();

		Activator.context = null;
	}
}