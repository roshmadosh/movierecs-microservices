package link.hiroshiprojects.movierecs.adminservice.resources;

import link.hiroshiprojects.movierecs.adminservice.models.AppUser;
import link.hiroshiprojects.movierecs.adminservice.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


/**
 * A controller for making admin-level requests for the application.
 */
@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('realm-admin')")
public class UsersResource {
    private final Logger logger = LoggerFactory.getLogger(UsersResource.class);
    private final UsersService usersService;

    public UsersResource(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> getUsers(@AuthenticationPrincipal Jwt jwt) {
       String adminToken = jwt.getTokenValue();
       try {
           List<AppUser> users = usersService.getUsers(adminToken);
           return ResponseEntity.ok().body(users);
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.badRequest().body(Collections.emptyList());
       }

    }

    /**
     * Register a user to the application.
     */
    @PostMapping
    public ResponseEntity<AppUser> registerUser(@AuthenticationPrincipal Jwt jwt, @RequestBody String email) {
        String adminToken = jwt.getTokenValue();

        // check if user exists in auth server
        if (!usersService.emailExists(adminToken, email)) {
            logger.warn("Account not found in auth server: " + email);
            return ResponseEntity.badRequest().body(null);
        }

        AppUser user = usersService.registerUser(adminToken, email);
        return ResponseEntity.ok().body(user);
    }


}
