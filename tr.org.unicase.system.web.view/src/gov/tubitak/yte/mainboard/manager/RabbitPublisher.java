package gov.tubitak.yte.mainboard.manager;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitPublisher implements CommandProvider {

	private static final String RABBIT_HOST = "localhost";
	private static final String EXCHANGE_NAME = "topic_logs";
	private static final String TASK_QUEUE_NAME = "esb.queue";
	
	@Override
	public String getHelp() {
		return "help";
	}
	
	public void _publish(CommandInterpreter ci) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RABBIT_HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel(12);

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");


		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		channel.queueBind(TASK_QUEUE_NAME, EXCHANGE_NAME, "");

		
		String message = ci.nextArgument();

		channel.basicPublish(EXCHANGE_NAME, TASK_QUEUE_NAME, null, message.getBytes());

		connection.close();
	}

}
