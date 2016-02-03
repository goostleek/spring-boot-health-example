# Spring Boot Examples: Custom health indicator with Hystrix as a circuit breaker

The project is just example implementation of custom [`HealthIndicator`](http://docs.spring.io/autorepo/docs/spring-boot/1.3.2.RELEASE/api/org/springframework/boot/actuate/health/HealthIndicator.html) and [metrics](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-metrics.html) aggregation. It also demonstrates Hystrix circuit breaker pattern usage.

## Prerequisites (to be installed)
 
- Mandatory:
 	- Docker >= 1.9.1
- Optional:
	- Gradle >= 2.10 (it's not required as the project includes Gradle Wrapper)
	- Lombok plugin (to support [Project Lombok](https://projectlombok.org/download.html) annotations in your IDE)

## Running the example
 
 1. Run dockerized Postgres database instance

		docker run --name postgres -e POSTGRES_PASSWORD=pass -d postgres

 2. Start the example using Gradle `bootRun` target

- go into project root (`spring-boot-health-example` directory)

- use Gradle Wrapper if you don't have standalone Gradle installed

	`./gradlew bootRun`

- or use just your local Gradle installation

	`gradle bootRun`

- start benchmark of the example application

		docker run --rm -t yokogawa/siege -d 1 -c 1000 -t 3m http://<ip-address-of-the-running-app>:8080/services/datetime

	This will start a [Siege](https://www.joedog.org/siege-home/) load test utility which will simulate 1000 simultaneous users to the  [http://localhost:8080/services/datetime](http://localhost:8080/services/datetime) service each will retry the request every second for 3 minutes

- go to your local [Hystrix Dashboard](http://localhost:8080/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8080%2Fhystrix.stream&delay=500&title=Example%20of%20Hystrix%20Dashboard) instance to see real time circuit breaker and HTTP requests status

- go to local [metrics endpoint](http://localhost:8080/metrics) to examine gathered metrics

## Importing into IDE

- IDEA users

	`./gradlew idea`

- Eclipse users

	`./gradlew eclipse`