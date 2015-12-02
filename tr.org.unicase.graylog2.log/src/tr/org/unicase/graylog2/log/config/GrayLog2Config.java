package tr.org.unicase.graylog2.log.config;

import java.net.InetSocketAddress;
import java.util.Dictionary;

import org.graylog2.gelfclient.GelfConfiguration;
import org.graylog2.gelfclient.GelfMessage;
import org.graylog2.gelfclient.GelfMessageBuilder;
import org.graylog2.gelfclient.GelfTransports;
import org.graylog2.gelfclient.transport.GelfTransport;

public class GrayLog2Config {

	private static GrayLog2Config INSTANCE = null;

	private GelfConfiguration gelfConfiguration = null;
	private GelfTransport gelfTransport = null;
	private GelfMessageBuilder gelfMessageBuilder = null;

	private GrayLog2Config() {
		refresh(null);
	}

	public static GrayLog2Config getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GrayLog2Config();
		return INSTANCE;
	}

	private Object getValue(Dictionary<String, ?> properties, String key, Object defaultValue) {
		if (properties == null || properties.isEmpty())
			return defaultValue;
		Object object = properties.get(key);
		return (object == null ? defaultValue : object);
	}

	public void refresh(Dictionary<String, ?> parameters) {
		String host = (String) getValue(parameters, Configuration.KEYS.HOST, Configuration.VALUES.HOST);
		String port = (String) getValue(parameters, Configuration.KEYS.PORT, Configuration.VALUES.PORT);
		String type = (String) getValue(parameters, Configuration.KEYS.TRANSPORT, Configuration.VALUES.TRANSPORT);
		GelfTransports transportType = GelfTransports.valueOf(type);
		
		if (this.gelfTransport != null)
			this.gelfTransport.stop();
		
		this.gelfConfiguration = new GelfConfiguration(new InetSocketAddress(host, Integer.parseInt(port))).transport(transportType).queueSize(512).connectTimeout(5000).reconnectDelay(1000).tcpNoDelay(true).sendBufferSize(32768);
		this.gelfTransport = GelfTransports.create(this.gelfConfiguration);
		this.gelfMessageBuilder = new GelfMessageBuilder(Configuration.EMPTY_MESSAGE, host);
	
	}

	public void send(GelfMessage message) throws InterruptedException {
		if (this.gelfTransport != null) {
			this.gelfTransport.send(message);
		}
	}

	public GelfMessageBuilder getMessageBuilder() {
		return this.gelfMessageBuilder;
	}

}
