package link.hiroshiprojects.movierecs.usersservice.resources;

import link.hiroshiprojects.movierecs.usersservice.models.AppUser;
import link.hiroshiprojects.movierecs.usersservice.models.MovieId;
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

    @PostMapping("/{userId}/favorites")
    public AppUser addMovieToFavorites(@PathVariable long userId,
                                       @RequestBody MovieIdsDTO movieIdsDTO) {
        System.out.println(movieIdsDTO);
        return userService.addMoviesToFavorites(userId, movieIdsDTO.getIds());
    }
}
