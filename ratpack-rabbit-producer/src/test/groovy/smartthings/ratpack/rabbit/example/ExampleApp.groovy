package smartthings.ratpack.rabbit.example

import groovy.util.logging.Slf4j
import ratpack.config.ConfigData
import ratpack.groovy.test.embed.GroovyEmbeddedApp
import ratpack.guice.Guice
import smartthings.ratpack.rabbit.RabbitProducerModule

@Slf4j
class ExampleApp {

	static String getTestRabbitServer() {
		return System.getenv("RABBIT_SERVER") ?: '192.168.99.100'
	}

	static def getConfig() {
		def config = new RabbitProducerModule.Config()
		config.setHostName(getTestRabbitServer())
		config.setUsername("guest")
		config.setPassword("guest")
		config.setPortNumber(5672)
		config.setVirtualHost("/")
		return config
	}

	static def getExampleApp() {
		return GroovyEmbeddedApp.of {
			registry Guice.registry {
				it.moduleConfig(RabbitProducerModule, getConfig())
				it.bind(TestQueueService)
			}

			handlers {
				all {
					TestQueueService testQueueService = registry.get(TestQueueService)
					def helloWorld = "Hello World".bytes
					testQueueService.send(helloWorld).onError({ t ->
						response.status(500)
						t.printStackTrace()
						render t.toString()
					}).then {
						render "working"
					}
				}
			}

		}
	}
}
