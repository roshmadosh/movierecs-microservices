package link.hiroshiprojects.movierecs.usersservice;

import link.hiroshiprojects.movierecs.usersservice.models.MovieDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class FeignClients {

    @FeignClient("assets-service")
    public interface MlClient {

       @GetMapping("/details/{id}")
       MovieDetails getDetails(@PathVariable long id);
    }
}
