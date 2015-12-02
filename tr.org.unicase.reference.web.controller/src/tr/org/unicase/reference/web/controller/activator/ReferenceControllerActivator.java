package tr.org.unicase.reference.web.controller.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import tr.org.unicase.reference.model.ReferenceEntity;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;

public class ReferenceControllerActivator implements BundleActivator {

	public static HazelcastInstance client = null;

	@Override
	public void start(BundleContext context) throws Exception {

		try {
			ClientConfig clientConfig = new ClientConfig();
			clientConfig.getGroupConfig().setName("dev").setPassword("dev-pass");
			clientConfig.getNetworkConfig().setSmartRouting(true);
			clientConfig.getNetworkConfig().addAddress("localhost");
			SerializerConfig sc = new SerializerConfig();
			sc.setImplementation(new ReferenceEntitySerializer()).setTypeClass(ReferenceEntity.class);
			clientConfig.getSerializationConfig().addSerializerConfig(sc);
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
