package gov.tubitak.yte.mainboard.manager;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;

public class Activator implements BundleActivator, SynchronousBundleListener {

	private static Bundle[] bundles;

	public static synchronized Bundle[] getBundles() {
		return bundles;
	}

	@Override
	public void start(BundleContext context) throws Exception {

	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}

	@Override
	public void bundleChanged(BundleEvent event) {

	}
}
