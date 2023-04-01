package link.hiroshiprojects.movierecs.usersservice.resources;

import link.hiroshiprojects.movierecs.usersservice.models.AppUser;
import link.hiroshiprojects.movierecs.usersservice.models.MovieIdsDTO;
import link.hiroshiprojects.movierecs.usersservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/users", produces = "application/json")
public class UserResource {
    private final Logger logger = LoggerFactory.getLogger(UserResource.class);
    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users.
     */
    @GetMapping
    @PreAuthorize("hasRole('realm-admin')")
    public List<AppUser> getAllUsers() {
        return userService.getAll();
    }

    /**
     * Gets user details. The user making the request must either be an admin or
     * a user asking for his own details.
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('realm-admin') or #email == #jwt.claims['email']")
    public AppUser getUserByEmail(@RequestParam(name = "email") String email,
                                  @AuthenticationPrincipal Jwt jwt) {
        AppUser user = userService.getUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User '" + email + "' not found.");
        }
        return user;
    }

    /**
     * Saves a new user.
     * e.g. request body:
     *      { "email": "mynewuser@email.com" }
     */
    @PostMapping
    @PreAuthorize("hasRole('realm-admin')")
    public AppUser saveUser(@RequestBody AppUser appUser) {
        return userService.save(appUser);
    }

    /**
     * Add movies to a user's favorites.
     * e.g. request body:
     *      {
     *          "email": "user@email.com",
     *          "movieIds": [1,2,3]
     *      }
     */
    @PostMapping("/favorites")
    @PreAuthorize("hasRole('realm-admin') or #movieIdsDTO.email == #jwt.claims['email']")
    public AppUser addMovieToFavorites(@RequestBody MovieIdsDTO movieIdsDTO,
                                       @AuthenticationPrincipal Jwt jwt)
            throws UserPrincipalNotFoundException {
        return userService.addMoviesToFavorites(movieIdsDTO.getEmail(), movieIdsDTO.getMovieIds());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('realm-admin')")
    public long deleteUser(@RequestBody Map<String, String> request) {
        String email = request.getOrDefault("email", null);
        if (email == null) {
            logger.warn("User with email '" + email + "' not found.");
            return 0;
        }
        return userService.deleteUser(email);
    }
}
