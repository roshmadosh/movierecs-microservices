package link.hiroshiprojects.movierecs.usersservice.services;


import link.hiroshiprojects.movierecs.usersservice.models.AppUser;
import link.hiroshiprojects.movierecs.usersservice.models.MovieId;

import java.util.List;

public interface UserService {
    List<AppUser> getAll();
    AppUser save(AppUser appUser);
    AppUser addMoviesToFavorites(long userId, List<MovieId> movie);
}
