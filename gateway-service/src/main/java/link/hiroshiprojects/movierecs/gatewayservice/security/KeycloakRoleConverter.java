package link.hiroshiprojects.movierecs.gatewayservice.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to help read roles from a Keycloak JWT access token. Keycloak is an authorization
 * server that will grant access tokens to our endpoints.
 */
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        if (realmAccess == null || realmAccess.isEmpty()) {
            return Collections.emptyList();
        }

        Collection<GrantedAuthority> result = ((List<String>) realmAccess.get("roles"))
                .stream().map(name -> "ROLE_" + name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        System.out.println("*******************");
        System.out.println(result);
        return result;
    }
}
