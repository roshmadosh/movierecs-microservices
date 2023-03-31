package link.hiroshiprojects.movierecs.adminservice.services;

import com.netflix.discovery.EurekaClient;
import link.hiroshiprojects.movierecs.adminservice.models.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * A service class for using the Users-Service microservice.
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private EurekaClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    /**
     * Saves a user to the Users-Service microservice.
     */
    @Override
    public AppUser registerUser(String adminToken, String registerEmail) {
        String url = discoveryClient.getNextServerFromEureka("USERS-SERVICE", false)
                .getHomePageUrl();
        logger.info("Attempting to register user with email: " + registerEmail);

        AppUser user = new AppUser(registerEmail);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);

        HttpEntity<AppUser> httpEntity = new HttpEntity<>(user, headers);
        return restTemplate.postForObject(url + "/api/v1/users", httpEntity, AppUser.class);
    }

    @Override
    public boolean emailExists(String adminToken, String email) {
        String authServerURL = "http://localhost:9000";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Object[]> response = restTemplate.exchange(
                authServerURL + "/admin/realms/movierecs-realm/users?email=" + email + "&&exact=true",
                HttpMethod.GET, requestEntity, Object[].class);

        return response.getBody().length != 0;
    }

}
