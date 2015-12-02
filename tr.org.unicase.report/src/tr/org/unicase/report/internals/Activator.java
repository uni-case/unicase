package tr.org.unicase.report.internals;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import tr.org.unicase.report.UnicaseReport;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		UnicaseReport.clear();
	}

}
