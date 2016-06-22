package smartthings.ratpack.rabbit;

import com.google.inject.Inject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ratpack.service.Service;
import ratpack.service.StartEvent;
import ratpack.service.StopEvent;

import java.io.IOException;
import java.util.Optional;

public class RabbitConnectionService implements Service {

	private final RabbitProducerModule.Config config;

	private Connection connection;

	@Inject
	public RabbitConnectionService(RabbitProducerModule.Config config) {
		this.config = config;
	}

	@Override
	public void onStart(StartEvent event) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(config.getHostName());
		factory.setPort(config.getPortNumber());
		factory.setVirtualHost(config.getVirtualHost());
		factory.setUsername(config.getUsername());
		factory.setPassword(config.getPassword());
		connection = factory.newConnection();
	}

	@Override
	public void onStop(StopEvent event) throws Exception {
		connection.close(config.getCloseTimeoutMilliseconds());
	}

	public Optional<Channel> getNewChannel() throws IOException {
		return Optional.ofNullable(connection.createChannel());
	}

}
