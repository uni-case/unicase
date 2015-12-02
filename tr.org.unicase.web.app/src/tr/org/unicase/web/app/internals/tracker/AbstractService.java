package tr.org.unicase.web.app.internals.tracker;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import tr.org.unicase.web.app.api.IUnicaseManager;
import tr.org.unicase.web.app.internals.customizer.AbstractUnicaseServiceTrackerCustomizer;

public abstract class AbstractService<E, T extends IUnicaseManager<E>> {

	private Class<E> clazz;
	private BundleContext context;
	private ServiceTracker<E, E> serviceTracker;
	private AbstractUnicaseServiceTrackerCustomizer<E, T> customizer;

	public AbstractService(BundleContext context, Class<E> clazz) {
		this.context = context;
		this.clazz = clazz;
		init(this.context);
	}

	protected abstract void init(BundleContext context);

	protected final void setCustomizer(AbstractUnicaseServiceTrackerCustomizer<E, T> customizer) {
		this.customizer = customizer;
		this.serviceTracker = new ServiceTracker<E, E>(this.context, this.clazz, this.customizer);
	}

	public void track() {
		this.serviceTracker.open();
	}

	public void untrack() {
		this.serviceTracker.close();
		this.customizer.clear();
		this.customizer = null;
		this.serviceTracker = null;
	}

}
