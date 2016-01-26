package pl.jcommerce.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HealthExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthExampleApplication.class, args);
	}

}
