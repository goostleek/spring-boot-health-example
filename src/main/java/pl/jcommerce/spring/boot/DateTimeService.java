package pl.jcommerce.spring.boot;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/services")
@CacheConfig(cacheNames = DateTimeService.DATE_TIME_SERVICE_CACHE)
public class DateTimeService {

	static final String DATE_TIME_SERVICE_CACHE = "datetimeServiceCache";

	private final Cache datetimeCache;

	private final EntityManager em;

	@Autowired
	public DateTimeService(CacheManager cacheManager, EntityManager entityManager) {
		datetimeCache = cacheManager.getCache(DATE_TIME_SERVICE_CACHE);
		em = entityManager;
	}

	@CachePut
	@RequestMapping(value = "/datetime", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "getCachedDateTime")
	public ResponseEntity<String> getDateTime() throws InterruptedException {
		Thread.sleep(300);
		final Object dateTime = em.createNativeQuery("SELECT CURRENT_TIMESTAMP").getSingleResult();
		return ResponseEntity.ok(dateTime.toString());
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<String> getCachedDateTime() {
		return datetimeCache.get(SimpleKey.EMPTY, ResponseEntity.class);
	}

}
