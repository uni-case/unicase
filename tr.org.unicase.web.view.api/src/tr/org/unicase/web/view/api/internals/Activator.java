package tr.org.unicase.web.view.api.internals;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import tr.org.unicase.web.view.action.api.IActionFactory;
import tr.org.unicase.web.view.action.api.UnicaseActionManager;
import tr.org.unicase.web.view.api.internals.config.Configuration;

public class Activator implements BundleActivator, ServiceTrackerCustomizer<IActionFactory, IActionFactory> {

	private static BundleContext context = null;
	private ServiceTracker<IActionFactory, IActionFactory> actionFactoryServiceTracker = null;

	@Override
	public void start(BundleContext context) throws Exception {
		Activator.context = context;
		trackActionFactoryServices();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Activator.context = null;
		if (actionFactoryServiceTracker != null) {
			actionFactoryServiceTracker.close();
			actionFactoryServiceTracker = null;
		}
	}

	private void trackActionFactoryServices() {
		actionFactoryServiceTracker = new ServiceTracker<IActionFactory, IActionFactory>(context, IActionFactory.class, this);
		actionFactoryServiceTracker.open();
		Configuration.ActionManager.LOGGER.info(Configuration.ActionManager.LISTENING);
	}

	@Override
	public IActionFactory addingService(ServiceReference<IActionFactory> reference) {
		IActionFactory service = Activator.context.getService(reference);
		UnicaseActionManager.getInstance().addActionFactory(service);
		return service;
	}

	@Override
	public void removedService(ServiceReference<IActionFactory> reference, IActionFactory service) {
		UnicaseActionManager.getInstance().removeActionFactory(service);
	}

	@Override
	public void modifiedService(ServiceReference<IActionFactory> reference, IActionFactory service) {

	}
}