package pl.jcommerce.spring.boot;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UrlPathHelper;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomMetricAspect {

	private final GaugeService gaugeService;
	private final CounterService counterService;

	@AfterReturning(pointcut = "execution(* DateTimeService.*(..))")
	public void updateMetric() {
		final String lastExecutedMetric = "lastExecuted" + getMetricName();
		gaugeService.submit(lastExecutedMetric, System.currentTimeMillis());
		final String serviceCounter = getMetricName().substring(1);
		counterService.increment(serviceCounter);
	}

	private String getMetricName() {
		return getServicePath().replaceAll("/", ".");
	}

	private String getServicePath() {
		return new UrlPathHelper().getPathWithinApplication(getCurrentRequest());
	}

	private HttpServletRequest getCurrentRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}
}

