package link.hiroshiprojects.movierecs.usersservice.resources;

import link.hiroshiprojects.movierecs.usersservice.models.AppUser;
import link.hiroshiprojects.movierecs.usersservice.models.MovieIdsDTO;
import link.hiroshiprojects.movierecs.usersservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserResource {
    private final Logger logger = LoggerFactory.getLogger(UserResource.class);
    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public List<AppUser> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('admin') or #email == #jwt.claims['email']")
    public AppUser getUserByEmail(@RequestParam(name = "email") String email,
                                  @AuthenticationPrincipal Jwt jwt) {
        AppUser user = userService.getUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User '" + email + "' not found.");
        }
        return user;
    }


    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser) {
        try {
            AppUser user = userService.save(appUser);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Add movies to a user's favorites.
     * @param movieIdsDTO
     * Has two fields: "user_id" and "ids".
     * "ids" accepts a collection of MovieDetail
     *                    objects. E.g. request body:
     *                    {
     *                      "userId": 1,
     *                      "ids": [
     *                          {
     *                              // fields for MovieDetails object
     *                          }
     *                      ]
     *                    }
     * @return The user with the updated favorites field.
     */
    @PostMapping("/favorites")
    @PreAuthorize("hasRole('admin') or #movieIdsDTO.email == #jwt.claims['email']")
    public AppUser addMovieToFavorites(@RequestBody MovieIdsDTO movieIdsDTO,
                                       @AuthenticationPrincipal Jwt jwt)
            throws UserPrincipalNotFoundException {
        return userService.addMoviesToFavorites(movieIdsDTO.getEmail(), movieIdsDTO.getMovieIds());
    }
}
