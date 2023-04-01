package link.hiroshiprojects.movierecs.usersservice.services;


import link.hiroshiprojects.movierecs.usersservice.models.AppUser;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collection;
import java.util.List;

public interface UserService {
    List<AppUser> getAll();
    AppUser save(AppUser appUser);
    AppUser addMoviesToFavorites(String email, Collection<Long> movieIds) throws UserPrincipalNotFoundException;

    AppUser getUserByEmail(String email);

    long deleteUser(String email);

}
