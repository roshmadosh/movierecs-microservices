package link.hiroshiprojects.movierecs.gatewayservice.resource;

import link.hiroshiprojects.movierecs.gatewayservice.models.AppUser;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/gateway")
public class GatewayResource {

    @GetMapping
    public String smokeTest() {
        return "SMoke test successfuul!";
    }

    /**
     * Executed only if the user is an admin, or the user requesting the deletion is the user himself.
     */
    @PreAuthorize("hasRole('admin') or #userId == #jaydubtee.subject")
    @DeleteMapping(path = "/{userId}")
    public String deleteUser(@PathVariable String userId, @AuthenticationPrincipal Jwt jaydubtee) {
        // "subject" is the name of the field in the JWT token that contains the keycloak user's ID
        return "Deleted user with ID " + jaydubtee.getSubject();
    }

    /**
     * Permitted only if the user requesting the user details is the user himself.
     */
    @PostAuthorize("returnObject.userId == #jwt.subject")
    @GetMapping("/{userId}")
    public AppUser getUser(@PathVariable String userId, @AuthenticationPrincipal Jwt jwt) {
        return new AppUser.Builder()
                .firstName(jwt.getClaims().get("given_name").toString())
                .lastName(jwt.getClaims().get("family_name").toString())
                .userId(userId)
                .build();
    }




}
