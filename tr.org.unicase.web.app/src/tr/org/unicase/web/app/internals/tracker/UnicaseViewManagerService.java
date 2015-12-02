package tr.org.unicase.web.app.internals.tracker;

import org.osgi.framework.BundleContext;

import tr.org.unicase.web.app.internals.customizer.UnicaseViewFactoryServiceTrackerCustomizer;
import tr.org.unicase.web.app.internals.manager.UnicaseViewManager;
import tr.org.unicase.web.view.api.IViewFactory;

public class UnicaseViewManagerService extends AbstractService<IViewFactory, UnicaseViewManager> {

	public UnicaseViewManagerService(BundleContext context) {
		super(context, IViewFactory.class);
	}

	@Override
	protected void init(BundleContext context) {
		setCustomizer(new UnicaseViewFactoryServiceTrackerCustomizer(context));
	}

}
