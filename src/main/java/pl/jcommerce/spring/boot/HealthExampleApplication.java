package pl.jcommerce.spring.boot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

import pl.jcommerce.spring.boot.users.User;
import pl.jcommerce.spring.boot.users.UserRepository;

@SpringBootApplication
@EnableCaching
@EnableHystrix
@EnableHystrixDashboard
public class HealthExampleApplication {

	private static final int USERS_LIMIT = 1000;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		new SpringApplicationBuilder(HealthExampleApplication.class).web(true).run(args);
	}

	@PostConstruct
	private void initDb() {
		if (userRepository.count() < USERS_LIMIT) {
			userRepository.save(createUsers(USERS_LIMIT));
		}
	}

	private List<User> createUsers(int limit) {
		final List<User> users = new ArrayList<>();
		while (users.size() < limit) {
			final User newUser = User.builder()
					.firstName(generateName())
					.lastName(generateName())
					.age(RandomUtils.nextInt(100) + 1)
					.build();
			users.add(newUser);
		}

		return users;
	}

	private String generateName() {
		return StringUtils.capitalize(RandomStringUtils.randomAlphabetic(10).toLowerCase());
	}
}

