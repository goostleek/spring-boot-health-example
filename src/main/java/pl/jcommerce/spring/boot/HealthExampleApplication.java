package pl.jcommerce.spring.boot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableCaching
@EnableCircuitBreaker
@EnableHystrixDashboard
public class HealthExampleApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(HealthExampleApplication.class).web(true).run(args);
	}

}
