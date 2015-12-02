package tr.org.unicase.web.app.internals.customizer;

import org.osgi.framework.BundleContext;

import tr.org.unicase.authentication.api.IAuthenticationService;
import tr.org.unicase.web.app.api.IUnicaseManager;
import tr.org.unicase.web.app.internals.manager.UnicaseAuthenticationManager;

public class UnicaseAuthenticationServiceTrackerCustomizer extends AbstractUnicaseServiceTrackerCustomizer<IAuthenticationService, UnicaseAuthenticationManager> {

	public UnicaseAuthenticationServiceTrackerCustomizer(BundleContext context) {
		super(context);
	}

	@Override
	protected IUnicaseManager<IAuthenticationService> getManagerInstance() {
		return UnicaseAuthenticationManager.getInstance();
	}

}
