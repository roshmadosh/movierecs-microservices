package link.hiroshiprojects.movierecs.usersservice.services;

import link.hiroshiprojects.movierecs.usersservice.FeignClients;
import link.hiroshiprojects.movierecs.usersservice.models.AppUser;
import link.hiroshiprojects.movierecs.usersservice.models.MovieDetails;
import link.hiroshiprojects.movierecs.usersservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Service
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
        AppUser query = userRepository.findByEmail(appUser.getEmail());
        if (query != null) {
            return query;
        }
        return userRepository.save(appUser);
    }

    @Override
    public AppUser addMoviesToFavorites(long userId, List<Long> movieIds) {
        for (long id: movieIds) {
            MovieDetails details = mlClient.getDetails(id);
            if (details == null) {
               throw new RuntimeException("MovieID " + id + " not found.");
            }
        }
        AppUser user = userRepository.getReferenceById(userId);
        user.addMovieIdsToFavorites(movieIds);
        return userRepository.save(user);
    }
}
