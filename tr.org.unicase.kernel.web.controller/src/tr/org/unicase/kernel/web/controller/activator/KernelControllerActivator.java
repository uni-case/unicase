package tr.org.unicase.kernel.web.controller.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import tr.org.unicase.kernel.web.controller.internals.config.Configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public class KernelControllerActivator implements BundleActivator {
	
	public static HazelcastInstance client = null;

	@Override
	public void start(BundleContext context) throws Exception {
		try {
			ClientConfig clientConfig = new ClientConfig();
			clientConfig.getGroupConfig().setName(Configuration.Hazelcast.GROUP_NAME).setPassword(Configuration.Hazelcast.GROUP_PASS);
			clientConfig.getNetworkConfig().setSmartRouting(Configuration.Hazelcast.SMART_ROUTING);
			clientConfig.getNetworkConfig().addAddress(Configuration.Hazelcast.ADDRESS);
			clientConfig.setClassLoader(Thread.currentThread().getContextClassLoader());
			client = HazelcastClient.newHazelcastClient(clientConfig);
		} catch (Exception e) {
			client = null;
		} 
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (client != null)
			client.shutdown();
	}

}
