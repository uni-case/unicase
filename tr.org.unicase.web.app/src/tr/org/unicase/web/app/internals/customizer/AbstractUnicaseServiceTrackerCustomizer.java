package tr.org.unicase.web.app.internals.customizer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import tr.org.unicase.web.app.api.IUnicaseManager;

public abstract class AbstractUnicaseServiceTrackerCustomizer<E, M extends IUnicaseManager<E>> implements ServiceTrackerCustomizer<E, E> {

	private BundleContext context;

	public AbstractUnicaseServiceTrackerCustomizer(BundleContext context) {
		this.context = context;
	}

	@Override
	public E addingService(ServiceReference<E> reference) {
		E service = context.getService(reference);
		getManagerInstance().addService(service);
		return service;
	}

	@Override
	public void modifiedService(ServiceReference<E> reference, E service) {

	}

	@Override
	public void removedService(ServiceReference<E> reference, E service) {
		getManagerInstance().removeService(service);
	}

	public void clear() {
		getManagerInstance().clear();
	}

	protected abstract IUnicaseManager<E> getManagerInstance();

}
