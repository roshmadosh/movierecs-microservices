package link.hiroshiprojects.movierecs.adminservice.services;

import link.hiroshiprojects.movierecs.adminservice.models.AppUser;

public interface UsersService {
    AppUser registerUser(String adminToken, String registerEmail);

}
