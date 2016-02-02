package pl.jcommerce.spring.boot;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteSender;

@SpringBootApplication
@EnableCaching
@EnableCircuitBreaker
@EnableHystrixDashboard
public class HealthExampleApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(HealthExampleApplication.class).web(true).run(args);
	}

	@Bean
	public GraphiteReporter graphiteReporter(MetricRegistry metricRegistry, GraphiteSender graphiteSender) {
		final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry).build(graphiteSender);
		reporter.start(1, TimeUnit.SECONDS);
		return reporter;
	}

	@Bean
	GraphiteSender graphite(@Value("${docker.host}") String dockerHost) {
		return new Graphite(new InetSocketAddress(dockerHost, 2003));
	}
}
