package link.hiroshiprojects.movierecs.adminservice.services;

import link.hiroshiprojects.movierecs.adminservice.client.UsersClient;
import link.hiroshiprojects.movierecs.adminservice.models.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A service class for using the Users-Service microservice.
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Value("${keycloak.server.host}")
    private String keycloakServerHost;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsersClient usersClient;

    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Override
    public List<AppUser> getUsers(String adminToken) {
        logger.info("Attempting to fetch all users from USERS-SERVICE");

        return usersClient.getAppUsers("Bearer " + adminToken);
    }

    /**
     * Saves a user to the Users-Service microservice.
     */
    @Override
    public AppUser registerUser(String adminToken, String registerEmail) {
        logger.info("Attempting to register user with email: " + registerEmail);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", registerEmail);
        return usersClient.register("Bearer " + adminToken, requestBody);

    }

    @Override
    public long deleteUser(String adminToken, String deleteEmail) {
        logger.info("Attempting to delete user from USERS-SERVICE: " + deleteEmail);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", deleteEmail);

        return usersClient.delete("Bearer " + adminToken, requestBody);
    }


    @Override
    public boolean emailExists(String adminToken, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + adminToken);
        headers.set("Content-type", "application/json");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object[]> response = restTemplate.exchange(
                keycloakServerHost + "/admin/realms/movierecs-realm/users?email=" + email + "&&exact=true",
                HttpMethod.GET, requestEntity, Object[].class);

        return response.getBody().length != 0;
    }

}
