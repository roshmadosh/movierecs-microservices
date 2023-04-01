package link.hiroshiprojects.movierecs.adminservice.services;

import com.netflix.discovery.EurekaClient;
import link.hiroshiprojects.movierecs.adminservice.models.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * A service class for using the Users-Service microservice.
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private RestTemplate restTemplate;

    private String usersServiceURL;

    public UsersServiceImpl(EurekaClient discoveryClient) {
        this.usersServiceURL = discoveryClient.getNextServerFromEureka("USERS-SERVICE", false)
                        .getHomePageUrl() + "/api/v1/users";
    }

    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Override
    public List<AppUser> getUsers(String adminToken) {
        logger.info("Attempting to fetch all users from USERS-SERVICE");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(usersServiceURL, HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<List<AppUser>>(){}).getBody();
    }

    /**
     * Saves a user to the Users-Service microservice.
     */
    @Override
    public AppUser registerUser(String adminToken, String registerEmail) {
        logger.info("Attempting to register user with email: " + registerEmail);

        AppUser user = new AppUser(registerEmail);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);

        HttpEntity<AppUser> httpEntity = new HttpEntity<>(user, headers);
        return restTemplate.postForObject(usersServiceURL, httpEntity, AppUser.class);
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
