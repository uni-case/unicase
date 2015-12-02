package tr.org.unicase.graylog2.log.internals;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

import tr.org.unicase.log.api.IUnicaseLogService;

public class Activator implements BundleActivator {

	private ServiceRegistration<IUnicaseLogService> logServiceReg;

	private void registerLogService(BundleContext context) {
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put(Constants.SERVICE_PID, GrayLog2LogServiceImpl.class.getName());
		logServiceReg = context.registerService(IUnicaseLogService.class, new GrayLog2LogServiceImpl(), properties);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		registerLogService(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		logServiceReg.unregister();
	}
}