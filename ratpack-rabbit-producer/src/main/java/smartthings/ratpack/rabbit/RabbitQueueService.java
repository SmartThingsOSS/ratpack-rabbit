package smartthings.ratpack.rabbit;

import com.google.inject.Inject;
import com.rabbitmq.client.Channel;
import ratpack.exec.Blocking;
import ratpack.exec.Operation;
import ratpack.service.DependsOn;
import ratpack.service.Service;
import ratpack.service.StartEvent;
import ratpack.service.StopEvent;

@DependsOn(RabbitConnectionService.class)
abstract public class RabbitQueueService implements Service {

	private Channel channel;
	private RabbitConnectionService rabbitConnectionService;

	@Inject
	public RabbitQueueService(RabbitConnectionService rabbitConnectionService) {
		this.rabbitConnectionService = rabbitConnectionService;
	}

	public abstract ChannelConfig getChannelConfig();

	@Override
	public void onStart(StartEvent event) throws Exception {
		channel = rabbitConnectionService.getNewChannel().get();
		ChannelConfig config = getChannelConfig();
		channel.queueDeclare(config.getQueueName(), config.getDurable(), config.getExclusive(), config.getAutoDelete(), config.getArguments());
	}

	@Override
	public void onStop(StopEvent event) throws Exception {
		channel.close();
	}

	public Operation send(byte[] body) {
		return Blocking.op(() -> {
			//TODO support more complicated sending including Basic Properties
			channel.basicPublish("", getChannelConfig().getQueueName(), null, body);
		});
	}

}
