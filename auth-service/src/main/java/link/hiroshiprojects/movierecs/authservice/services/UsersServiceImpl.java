package link.hiroshiprojects.movierecs.authservice.services;

import com.netflix.discovery.EurekaClient;
import link.hiroshiprojects.movierecs.authservice.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private EurekaClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public AppUser registerUser(String email) {
        String url = discoveryClient.getNextServerFromEureka("USERS-SERVICE", false).getHomePageUrl();
        return restTemplate.postForObject(url + "/api/v1/users", new AppUser(email), AppUser.class);
    }

    @Override
    public AppUser getUserByEmail(String email) {
        String url = discoveryClient.getNextServerFromEureka("USERS-SERVICE", false).getHomePageUrl();
        return restTemplate.getForObject(url + "/api/v1/users/user?email=" + email, AppUser.class);
    }
}
