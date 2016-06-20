package smartthings.ratpack.rabbit

import ratpack.registry.Registry
import ratpack.service.StartEvent
import ratpack.service.StopEvent
import ratpack.test.exec.ExecHarness
import spock.lang.AutoCleanup
import spock.lang.Ignore
import spock.lang.Specification

class RabbitConnectionServiceSpec extends Specification {

	@AutoCleanup
	ExecHarness harness = ExecHarness.harness()

	def "Fail on invalid config"() {
		given:
		RabbitConnectionService service
		RabbitProducerModule.Config config = new RabbitProducerModule.Config()

		when:
		harness.run {
			service = new RabbitConnectionService(config)
			service.onStart(new StartEvent() {
				@Override
				Registry getRegistry() {
					return Registry.empty()
				}

				@Override
				boolean isReload() {
					return false
				}
			})
		}

		then:
		thrown(IllegalArgumentException)
	}

	def "Fail on unreachable rabbit"() {
		given:
		RabbitConnectionService service
		RabbitProducerModule.Config config = new RabbitProducerModule.Config()
		config.setHostName("localhost")
		config.setPortNumber(1)

		when:
		harness.run {
			service = new RabbitConnectionService(config)
			service.onStart(new StartEvent() {
				@Override
				Registry getRegistry() {
					return Registry.empty()
				}

				@Override
				boolean isReload() {
					return false
				}
			})
		}

		then:
		thrown(ConnectException)
	}

	@Ignore
	def "Can start and stop as expected"() {
		given:
		RabbitProducerModule.Config config = new RabbitProducerModule.Config()
		RabbitConnectionService service

		when:
		harness.run {
			service = new RabbitConnectionService(config)
			service.onStart(new StartEvent() {
				@Override
				Registry getRegistry() {
					return Registry.empty()
				}

				@Override
				boolean isReload() {
					return false
				}
			})
		}

		and:
		harness.run {
			service.onStop(new StopEvent() {
				@Override
				Registry getRegistry() {
					return Registry.empty()
				}

				@Override
				boolean isReload() {
					return false
				}
			})
		}

		then:
		noExceptionThrown()
	}
}
