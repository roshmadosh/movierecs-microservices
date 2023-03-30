package link.hiroshiprojects.movierecs.adminservice.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter @Setter @NoArgsConstructor @ToString
public class AppUser {
    private long id;
    private String email;
    private Set<Long> favorites;

    public AppUser(String email) {
        this.email = email;
    }
}
