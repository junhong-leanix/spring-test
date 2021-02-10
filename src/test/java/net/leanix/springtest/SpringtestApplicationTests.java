package net.leanix.springtest;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@SpringBootTest
@ContextConfiguration(initializers = {SpringtestApplicationTests.Initializer.class})
class SpringtestApplicationTests {

	static class Initializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		private static final PostgreSQLContainer SQL_CONTAINER = (PostgreSQLContainer) new PostgreSQLContainer("postgres:11.5")
			.withTmpFs(java.util.Map.of("/var/lib/postgresql/data", "rw"));

		private static void startContainers() {
			Startables.deepStart(Stream.of(SQL_CONTAINER)).join();
			// we can add further containers
			// here like rabbitmq or other databases
		}

		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			startContainers();
			TestPropertyValues.of(
				"spring.datasource.url=" + SQL_CONTAINER.getJdbcUrl(),
				"spring.datasource.username=" + SQL_CONTAINER.getUsername(),
				"spring.datasource.password=" + SQL_CONTAINER.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@Test
	void contextLoads() {
	}

}
