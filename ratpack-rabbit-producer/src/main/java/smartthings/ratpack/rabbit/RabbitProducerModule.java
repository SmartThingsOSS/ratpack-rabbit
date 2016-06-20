package smartthings.ratpack.rabbit;

import com.google.inject.Scopes;
import ratpack.guice.ConfigurableModule;

import java.util.concurrent.TimeUnit;

public class RabbitProducerModule extends ConfigurableModule<RabbitProducerModule.Config> {

	@Override
	protected void configure() {
		bind(RabbitConnectionService.class).in(Scopes.SINGLETON);
	}

	public static class Config {

		private int closeTimeoutMilliseconds = new Long(TimeUnit.SECONDS.toMillis(10)).intValue();
		private String hostName;
		private int portNumber;
		private String virtualHost;
		private String username;
		private String password;

		public Config() {
		}

		public int getCloseTimeoutMilliseconds() {
			return closeTimeoutMilliseconds;
		}

		public void setCloseTimeoutMilliseconds(int closeTimeoutMilliseconds) {
			this.closeTimeoutMilliseconds = closeTimeoutMilliseconds;
		}

		public String getHostName() {
			return hostName;
		}

		public void setHostName(String hostName) {
			this.hostName = hostName;
		}

		public int getPortNumber() {
			return portNumber;
		}

		public void setPortNumber(int portNumber) {
			this.portNumber = portNumber;
		}

		public String getVirtualHost() {
			return virtualHost;
		}

		public void setVirtualHost(String virtualHost) {
			this.virtualHost = virtualHost;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
}
