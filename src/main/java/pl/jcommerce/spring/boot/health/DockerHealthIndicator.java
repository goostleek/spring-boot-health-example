package pl.jcommerce.spring.boot.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

@Component
public class DockerHealthIndicator extends AbstractHealthIndicator {

	private final DockerPingService dockerPingService;

	@Autowired
	public DockerHealthIndicator(@Value("${docker.endpoint}") String dockerUrl) {
		final Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(dockerUrl)
				.build();
		dockerPingService = retrofit.create(DockerPingService.class);
	}

	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		final Response<ResponseBody> pingResponse = dockerPingService.ping().execute();
		if (pingResponse.isSuccess()) {
			builder.up();
			builder.withDetail("details", "Docker API available");
		}
	}

	private interface DockerPingService {
		@GET("_ping")
		Call<ResponseBody> ping();
	}
}
