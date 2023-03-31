package link.hiroshiprojects.movierecs.adminservice.resources;

import link.hiroshiprojects.movierecs.adminservice.models.AppUser;
import link.hiroshiprojects.movierecs.adminservice.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * A controller for making admin-level requests for the application.
 */
@RestController
@RequestMapping("/api/v1/admin")
public class AdminResource {
    private final Logger logger = LoggerFactory.getLogger(AdminResource.class);
    private final UsersService usersService;

    public AdminResource(UsersService usersService) {
        this.usersService = usersService;
    }


    /**
     * Register a user to the application.
     * e.g. .../api/v1/auth/register?email=example@email.com
     */
    @GetMapping("/register")
    public ResponseEntity<AppUser> registerUser(@AuthenticationPrincipal Jwt jwt, @RequestParam String email) {
        String adminToken = jwt.getTokenValue();

        // check if user exists in auth server
        if (!usersService.emailExists(adminToken, email)) {
            logger.warn("USER NOT FOUND: " + email);
            return ResponseEntity.badRequest().body(null);
        }

        AppUser user = usersService.registerUser(adminToken, email);
        return ResponseEntity.ok().body(user);
    }
}
