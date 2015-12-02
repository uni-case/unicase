package tr.org.unicase.web.app.internals.customizer;

import org.osgi.framework.BundleContext;

import tr.org.unicase.log.api.IUnicaseLogService;
import tr.org.unicase.web.app.api.IUnicaseManager;
import tr.org.unicase.web.app.internals.manager.UnicaseLogManager;

public class UnicaseLogServiceTrackerCustomizer extends AbstractUnicaseServiceTrackerCustomizer<IUnicaseLogService, UnicaseLogManager> {

	public UnicaseLogServiceTrackerCustomizer(BundleContext context) {
		super(context);
	}

	@Override
	protected IUnicaseManager<IUnicaseLogService> getManagerInstance() {
		return UnicaseLogManager.getInstance();
	}

}
