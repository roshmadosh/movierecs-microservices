package link.hiroshiprojects.movierecs.usersservice.resources;

import link.hiroshiprojects.movierecs.usersservice.models.AppUser;
import link.hiroshiprojects.movierecs.usersservice.models.MovieDetails;
import link.hiroshiprojects.movierecs.usersservice.models.MovieIdsDTO;
import link.hiroshiprojects.movierecs.usersservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserResource {
    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<AppUser> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/user")
    public AppUser getUserByEmail(@RequestParam(name = "email") String email) {
        AppUser user = userService.getUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User '" + email + "' not found.");
        }
        return user;
    }

    @PostMapping
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
    public AppUser addMovieToFavorites(@RequestBody MovieIdsDTO movieIdsDTO) {
        return userService.addMoviesToFavorites(movieIdsDTO.getUserId(), movieIdsDTO.getMovieIds());
    }
}
