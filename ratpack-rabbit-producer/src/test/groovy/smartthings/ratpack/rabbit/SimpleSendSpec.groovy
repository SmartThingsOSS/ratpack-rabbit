package smartthings.ratpack.rabbit

import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import smartthings.ratpack.rabbit.example.ExampleApp
import spock.lang.Shared
import spock.lang.Specification

class SimpleSendSpec extends Specification{

	@Shared
	ApplicationUnderTest aut = ExampleApp.getExampleApp()

	@Delegate
	TestHttpClient client = aut.httpClient

	def "Simple Send"(){
		when:
		def resp = get("/")

		then:
		resp.body.text == "working"
		resp.statusCode == 200

	}
}
