package link.hiroshiprojects.movierecs.authservice.resources;

import link.hiroshiprojects.movierecs.authservice.models.AppUser;
import link.hiroshiprojects.movierecs.authservice.services.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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


    /**
     * Intended to be entry-point for outside requests. Logs the user in and
     * "registers" the user to Users service if user is not found in the Users
     * service database.
     */
    @GetMapping("/login")
    public AppUser login(OAuth2AuthenticationToken token) {
        AppUser user = usersService.registerUser(token.getPrincipal().getAttribute("email"));
        return user;
    }


    @GetMapping("/user")
    public AppUser getUser(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        AppUser user = usersService.getUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User '" + email + "' not found.");
        }
        return user;
    }

}
