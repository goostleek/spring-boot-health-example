package pl.jcommerce.spring.boot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
@CacheConfig(cacheNames = "dateTimeService")
public class DateTimeService {

	private final Random random = new Random();

	@RequestMapping(value = "/datetime", method = RequestMethod.GET)
	public ResponseEntity<?> getDateTime() throws InterruptedException {
		final ResponseEntity<?> response;
		if (random.nextInt(10) > 0) {
			response = ResponseEntity.ok(LocalDateTime.now().toString());
		} else {
			final HttpStatus error = HttpStatus.INTERNAL_SERVER_ERROR;
			response = ResponseEntity.status(error).body(error);
		}
		Thread.sleep(300);
		return response;
	}

	@Cacheable
	@RequestMapping(value = "/date", method = RequestMethod.GET)
	public ResponseEntity<String> getDate() throws InterruptedException {
		Thread.sleep(300);
		return ResponseEntity.ok(LocalDate.now().toString());
	}

}
