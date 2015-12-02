package tr.org.unicase.web.app.activator.internals;

import javax.servlet.ServletException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import tr.org.unicase.web.app.internals.HealthCheckServlet;
import tr.org.unicase.web.app.internals.ResourceProvider;
import tr.org.unicase.web.app.internals.SimpleVaadinServlet;
import tr.org.unicase.web.app.internals.config.Configuration;

public class HttpServiceTrackerCustomizer implements ServiceTrackerCustomizer<HttpService, HttpService>, BundleListener {

	private BundleContext context;
	private HttpService httpService;
	private ResourceProvider resourceProvider;

	public HttpServiceTrackerCustomizer(BundleContext context) {
		this.context = context;
		resourceProvider = new ResourceProvider();
		handleStartedBundles(context);
		context.addBundleListener(this);
	}

	@Override
	public HttpService addingService(ServiceReference<HttpService> reference) {
		httpService = context.getService(reference);

		try {
			httpService.registerServlet(Configuration.HttpTracker.WEB_CONTEXTPATH, new SimpleVaadinServlet(), null, resourceProvider);
			httpService.registerServlet(Configuration.HttpTracker.HEALTH_CONTEXTPATH, new HealthCheckServlet(), null, null);
			httpService.registerResources(Configuration.HttpTracker.RESOURCE_CONTEXTPATH, Configuration.HttpTracker.RESOURCE_CONTEXTPATH, resourceProvider);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (NamespaceException e) {
			e.printStackTrace();
		}

		return httpService;
	}

	@Override
	public void modifiedService(ServiceReference<HttpService> reference, HttpService service) {
	}

	@Override
	public void removedService(ServiceReference<HttpService> reference, HttpService service) {
		httpService.unregister(Configuration.HttpTracker.WEB_CONTEXTPATH);
		httpService.unregister(Configuration.HttpTracker.HEALTH_CONTEXTPATH);

	}

	protected void handleStartedBundles(BundleContext context) {
		for (Bundle bundle : context.getBundles()) {
			String name = bundle.getSymbolicName();
			if (bundle.getState() == Bundle.ACTIVE && name.startsWith(Configuration.HttpTracker.RESOURCE_BUNDLENAME)) {
				resourceProvider.add(bundle);
			}
		}
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		String name = event.getBundle().getSymbolicName();
		if (name.startsWith(Configuration.HttpTracker.RESOURCE_BUNDLENAME)) {
			if (event.getType() == BundleEvent.STARTED) {
				resourceProvider.add(event.getBundle());
			} else if (event.getType() == BundleEvent.STOPPED) {
				resourceProvider.remove(event.getBundle());
			}
		}
	}

	public void reset() {
		resourceProvider = null;
		context.removeBundleListener(this);
	}

}
