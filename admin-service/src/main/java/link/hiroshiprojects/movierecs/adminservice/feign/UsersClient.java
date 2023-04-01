package link.hiroshiprojects.movierecs.adminservice.feign;

import feign.Headers;
import feign.Param;
import link.hiroshiprojects.movierecs.adminservice.models.AppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@FeignClient(name = "users-service", path = "api/v1/users", configuration = FeignConfig.class)
public interface UsersClient {

    @GetMapping
    List<AppUser> getAppUsers(@RequestHeader("Authorization") String token);

    @PostMapping
    AppUser register(@RequestHeader("Authorization") String token,
                     @RequestBody Map<String, String> request);

    @DeleteMapping
    long delete(@RequestHeader("Authorization") String token,
                     @RequestBody Map<String, String> request);

}
