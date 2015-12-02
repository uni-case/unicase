package tr.org.unicase.web.app.internals.tracker;

import org.osgi.framework.BundleContext;

import tr.org.unicase.log.api.IUnicaseLogService;
import tr.org.unicase.web.app.internals.customizer.UnicaseLogServiceTrackerCustomizer;
import tr.org.unicase.web.app.internals.manager.UnicaseLogManager;

public class UnicaseLogManagerService extends AbstractService<IUnicaseLogService, UnicaseLogManager> {

	public UnicaseLogManagerService(BundleContext context) {
		super(context, IUnicaseLogService.class);
	}

	@Override
	protected void init(BundleContext context) {
		setCustomizer(new UnicaseLogServiceTrackerCustomizer(context));
	}
	
	/*@SuppressWarnings("unchecked")
	private void handlePreviouslyRegisteredLogService() {
		try {
			String filterString = "(" + Constants.OBJECTCLASS + "=" + IUnicaseLogService.class.getName() + ")";
			ServiceReference<IUnicaseLogService>[] serviceReferences = (ServiceReference<IUnicaseLogService>[]) context.getServiceReferences(IUnicaseLogService.class.getName(), filterString);
			if (serviceReferences != null) {
				IUnicaseLogService service = null;
				if (serviceReferences.length  > 0) {
					for (ServiceReference<IUnicaseLogService> serviceReference : serviceReferences) {
						service = context.getService(serviceReference);
						UnicaseLogManager.getInstance().addService(service);
					}
				}
			}
		} catch (InvalidSyntaxException e) {
			LOGGER.info("There is a error while handling previously registered log service: " + e.getMessage());
			e.printStackTrace();
		}
	}*/

}
