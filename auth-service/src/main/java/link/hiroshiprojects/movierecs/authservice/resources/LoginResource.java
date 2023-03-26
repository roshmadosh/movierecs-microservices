package link.hiroshiprojects.movierecs.authservice.resources;

import link.hiroshiprojects.movierecs.authservice.models.AppUser;
import link.hiroshiprojects.movierecs.authservice.services.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginResource {
    private UsersService usersService;

    public LoginResource(UsersService usersService) {
        this.usersService = usersService;
    }


    @GetMapping("/login")
    public AppUser getLoggedIn(@AuthenticationPrincipal OAuth2User principal) {
        AppUser user = usersService.registerUser(principal.getAttribute("email"));
        return user;
    }

}
