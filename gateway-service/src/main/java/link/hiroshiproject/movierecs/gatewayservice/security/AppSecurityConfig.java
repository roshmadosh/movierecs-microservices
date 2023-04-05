package link.hiroshiproject.movierecs.gatewayservice.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class AppSecurityConfig {
    @Autowired
    private ReactiveClientRegistrationRepository clientRegistrationRepository;
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
       http
               .authorizeExchange(exchange -> exchange
                       .anyExchange().authenticated())
               .logout(logout -> logout
                       .logoutSuccessHandler(oidcLogoutSuccessHandler()))
               .oauth2Login();

       return http.build();
    }

    private ServerLogoutSuccessHandler oidcLogoutSuccessHandler() {
        System.out.println("LOGOUTTTTTTTTTTTT");
        log.info("LOGOUT commencing through handler...");
        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(this.clientRegistrationRepository);

        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");

        return oidcLogoutSuccessHandler;
    }
}
