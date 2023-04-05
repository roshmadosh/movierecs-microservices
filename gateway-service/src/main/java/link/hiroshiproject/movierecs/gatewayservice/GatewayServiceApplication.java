package link.hiroshiproject.movierecs.gatewayservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {
	public static final String PATH_PREFIX = "/api/v1";

	@Autowired
	private TokenRelayGatewayFilterFactory relayFilter;

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path(PATH_PREFIX + "/admin/**")
						.filters(f -> f
								.filter(relayFilter.apply())
								.rewritePath(PATH_PREFIX + "/admin/?(?<remaining>.*)", "/${remaining}")
						)
						.uri("lb://ADMIN-SERVICE"))
				.route(p -> p
						.path(PATH_PREFIX + "/users/**")
						.filters(f -> f
								.filter(relayFilter.apply())
								.rewritePath(PATH_PREFIX + "/users/?(?<remaining>.*)", "/${remaining}")
						)
						.uri("lb://USERS-SERVICE"))
				.build();
	}


}
