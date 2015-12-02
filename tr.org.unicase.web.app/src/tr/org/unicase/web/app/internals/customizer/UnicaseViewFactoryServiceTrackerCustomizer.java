package tr.org.unicase.web.app.internals.customizer;

import org.osgi.framework.BundleContext;

import tr.org.unicase.web.app.api.IUnicaseManager;
import tr.org.unicase.web.app.internals.manager.UnicaseViewManager;
import tr.org.unicase.web.view.api.IViewFactory;

public class UnicaseViewFactoryServiceTrackerCustomizer extends AbstractUnicaseServiceTrackerCustomizer<IViewFactory, UnicaseViewManager> {

	public UnicaseViewFactoryServiceTrackerCustomizer(BundleContext context) {
		super(context);
	}

	@Override
	protected IUnicaseManager<IViewFactory> getManagerInstance() {
		return UnicaseViewManager.getInstance();
	}


}
