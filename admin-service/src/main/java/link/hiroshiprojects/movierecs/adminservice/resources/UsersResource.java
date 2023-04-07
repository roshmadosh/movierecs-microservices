package link.hiroshiprojects.movierecs.adminservice.resources;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import link.hiroshiprojects.movierecs.adminservice.models.AppUser;
import link.hiroshiprojects.movierecs.adminservice.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A controller for making admin-level requests for the application.
 */
@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('admin')")
@CircuitBreaker(name = "users")
public class UsersResource {
    private final Logger logger = LoggerFactory.getLogger(UsersResource.class);
    private final UsersService usersService;

    public UsersResource(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public List<AppUser> getUsers(@AuthenticationPrincipal Jwt jwt) {
       String adminToken = jwt.getTokenValue();
       return usersService.getUsers(adminToken);
    }

    /**
     * Register a user to the application.
     */
    @PostMapping
    public ResponseEntity<AppUser> registerUser(@AuthenticationPrincipal Jwt jwt,
                                                @RequestBody Map<String, String> request) {

        String adminToken = jwt.getTokenValue();
        String email = request.getOrDefault("email", null);
        if (email == null) {
            String message = "Parameter 'email' not included in response body";
            logger.warn(message);
            return ResponseEntity.badRequest().body(null);
        }

        // check if user exists in auth server
        if (!usersService.emailExists(adminToken, email)) {
            String message = "Account '" + email + "' not found in server.";
            logger.warn(message);
            return ResponseEntity.badRequest().body(null);
        }
        AppUser user = usersService.registerUser(adminToken, email);
        return ResponseEntity.ok(user);
    }

    /**
     * Delete a user from the application.
     */
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteUser(@AuthenticationPrincipal Jwt jwt,
                           @RequestBody Map<String, String> request) {

        String adminToken = jwt.getTokenValue();
        Map<String, Object> response = new HashMap<>();
        String email = request.getOrDefault("email", null);
        if (email == null) {
            String message = "Required request body parameter 'email' missing.";
            logger.warn(message);
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
        // check if user exists in auth server
        if (!usersService.emailExists(adminToken, email)) {
            String message = "Account not found in auth server: " + email;
            logger.warn(message);
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }

        long userDeleted = usersService.deleteUser(adminToken, email);
        if (userDeleted != 1) {
            String message = "Failed user deletion, returned item: " + userDeleted;
            logger.warn(message);
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "User '" + email + "' deleted successfully.");
        return ResponseEntity.ok(response);
    }

}
