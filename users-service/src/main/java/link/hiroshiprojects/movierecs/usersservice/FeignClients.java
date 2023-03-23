package link.hiroshiprojects.movierecs.usersservice;

import link.hiroshiprojects.movierecs.usersservice.models.MovieId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class FeignClients {

    @FeignClient("ml-service")
    public interface MlClient {

       @GetMapping("api/v1/ml/assets/details/{id}")
       MovieId getDetails(@PathVariable long id);
    }
}
