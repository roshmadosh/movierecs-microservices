package link.hiroshiprojects.movierecs.usersservice.services;

import jakarta.transaction.Transactional;
import link.hiroshiprojects.movierecs.usersservice.FeignClients;
import link.hiroshiprojects.movierecs.usersservice.models.AppUser;
import link.hiroshiprojects.movierecs.usersservice.models.MovieDetails;
import link.hiroshiprojects.movierecs.usersservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FeignClients.MlClient mlClient;

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<AppUser> getAll() {
        return userRepository.findAll();
    }

    @Override
    public AppUser save(AppUser appUser) {
        AppUser query = userRepository.findByEmail(appUser.getEmail()).orElse(null);
        if (query != null) {
            return query;
        }
        return userRepository.save(appUser);
    }

    @Override
    public AppUser addMoviesToFavorites(String email, Collection<Long> movieIds)
            throws UserPrincipalNotFoundException {
        for (long id: movieIds) {
            MovieDetails details = mlClient.getDetails(id);
            if (details == null) {
               throw new RuntimeException("MovieID " + id + " not found.");
            }
        }
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserPrincipalNotFoundException(email));
        user.addMovieIdsToFavorites(movieIds);
        return userRepository.save(user);
    }

    @Override
    public AppUser getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public long deleteUser(String email) {
        try {
            return userRepository.deleteByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
