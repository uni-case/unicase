package tr.org.unicase.kernel.web.view.action.internals;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import tr.org.unicase.kernel.web.view.action.internals.factory.KernelViewActionFactory;
import tr.org.unicase.web.view.action.api.IActionFactory;

public class Activator implements BundleActivator {

	private ServiceRegistration<IActionFactory> registerService;

	@Override
	public void start(BundleContext context) throws Exception {
		registerService = context.registerService(IActionFactory.class, new KernelViewActionFactory(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (registerService != null)
			registerService.unregister();
	}
}