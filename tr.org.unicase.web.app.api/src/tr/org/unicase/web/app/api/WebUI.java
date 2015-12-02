package tr.org.unicase.web.app.api;

import java.util.Stack;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import tr.org.unicase.kernel.web.view.MainWindow;
import tr.org.unicase.web.view.api.Module;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public abstract class WebUI extends UI implements MainWindow, ModuleOperation, ServiceTrackerCustomizer<Module, Module> {

	private ServiceTracker<Module, Module> tracker;
	private BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
	private Stack<Component> componentStack = new Stack<Component>();

	@Override
	protected void init(VaadinRequest request) {
		initUI(request);
		tracker = new ServiceTracker<Module, Module>(context, Module.class, this);
		tracker.open();
	}

	@Override
	public final Module addingService(ServiceReference<Module> reference) {
		final Module provider = context.getService(reference);
		accessSynchronously(new Runnable() {
			@Override
			public void run() {
				addModule(provider);
			}
		});
		return provider;
	}

	@Override
	public final void removedService(ServiceReference<Module> reference, final Module service) {
		accessSynchronously(new Runnable() {
			@Override
			public void run() {
				removeModule(service);
			}
		});
	}

	@Override
	public void modifiedService(ServiceReference<Module> reference, Module service) {
		

	}
	
	protected void addViewToStack(Component component) {
		componentStack.add(component);
	}
	
	protected boolean isViewStackEmpty() {
		return componentStack.isEmpty();
	}
	
	protected Component getViewFromStack() {
		return componentStack.pop();
	}

	protected abstract void initUI(VaadinRequest request);
}
 