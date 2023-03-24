package link.hiroshiprojects.movierecs.authservice.resources;

import link.hiroshiprojects.movierecs.authservice.services.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginResource {
    private UsersService usersService;

    public LoginResource(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/saveUser")
    public Map<String, Object> getLoggedIn(@AuthenticationPrincipal OAuth2User principal) {
        long id = usersService.registerUser(principal.getAttribute("email"));
        Map<String, Object> resp = new HashMap<>();
        resp.put("id", id);
        return resp;
    }



}
