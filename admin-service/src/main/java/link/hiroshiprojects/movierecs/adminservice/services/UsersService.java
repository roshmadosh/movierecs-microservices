package link.hiroshiprojects.movierecs.adminservice.services;

import link.hiroshiprojects.movierecs.adminservice.models.AppUser;

import java.util.List;

public interface UsersService {
    List<AppUser> getUsers(String adminToken);
    AppUser registerUser(String adminToken, String registerEmail);

    long deleteUser(String adminToken, String deleteEmail);

    boolean emailExists(String adminToken, String email);

}
