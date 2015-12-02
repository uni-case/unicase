package tr.org.unicase.web.app.internals.tracker;

import org.osgi.framework.BundleContext;

import tr.org.unicase.authentication.api.IAuthenticationService;
import tr.org.unicase.web.app.internals.customizer.UnicaseAuthenticationServiceTrackerCustomizer;
import tr.org.unicase.web.app.internals.manager.UnicaseAuthenticationManager;

public class UnicaseAuthenticationManagerService extends AbstractService<IAuthenticationService, UnicaseAuthenticationManager> {

	public UnicaseAuthenticationManagerService(BundleContext context) {
		super(context,IAuthenticationService.class );
	}
	
	@Override
	protected void init(BundleContext context) {
		setCustomizer(new UnicaseAuthenticationServiceTrackerCustomizer(context));
	}

}
