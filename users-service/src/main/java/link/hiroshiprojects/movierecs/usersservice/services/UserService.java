package link.hiroshiprojects.movierecs.usersservice.services;


import link.hiroshiprojects.movierecs.usersservice.models.AppUser;

import java.util.List;

public interface UserService {
    List<AppUser> getAll();
    AppUser save(AppUser appUser);
    AppUser addMoviesToFavorites(long userId, List<Long> movieIds);

    AppUser getUserByEmail(String email);

}
