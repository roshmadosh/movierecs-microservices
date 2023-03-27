package link.hiroshiprojects.movierecs.authservice.services;

import link.hiroshiprojects.movierecs.authservice.models.AppUser;

public interface UsersService {
    AppUser registerUser(String email);
    AppUser getUserByEmail(String email);
}
