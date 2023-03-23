package link.hiroshiprojects.movierecs.usersservice.services;

import com.netflix.discovery.converters.Auto;
import link.hiroshiprojects.movierecs.usersservice.FeignClients;
import link.hiroshiprojects.movierecs.usersservice.models.AppUser;
import link.hiroshiprojects.movierecs.usersservice.models.MovieDetails;
import link.hiroshiprojects.movierecs.usersservice.models.MovieId;
import link.hiroshiprojects.movierecs.usersservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        return userRepository.save(appUser);
    }

    @Override
    public AppUser addMoviesToFavorites(long userId, List<MovieId> movieIds) {
        for (MovieId obj: movieIds) {
            if (mlClient.getDetails(obj.getId()) == null) {
               throw new RuntimeException("MovieID " + obj.getId() + " not found.");
            }
        }
        AppUser user = userRepository.getReferenceById(userId);
        user.addMovieIdsToFavorites(movieIds);
        return userRepository.save(user);
    }
}
