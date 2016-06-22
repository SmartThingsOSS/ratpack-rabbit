package smartthings.ratpack.rabbit.example

import com.google.inject.Inject
import smartthings.ratpack.rabbit.ChannelConfig
import smartthings.ratpack.rabbit.RabbitConnectionService
import smartthings.ratpack.rabbit.RabbitQueueService

import javax.inject.Singleton

@Singleton
class TestQueueService extends RabbitQueueService {

	private ChannelConfig channelConfig = new ChannelConfig(queueName: "test")

	@Inject
	TestQueueService(RabbitConnectionService rabbitConnectionService) {
		super(rabbitConnectionService)
	}

	@Override
	public ChannelConfig getChannelConfig() {
		return channelConfig
	}

	@Override
	String getName() {
		return "Rabbit TEST queue Service"
	}
}
