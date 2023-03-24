package link.hiroshiprojects.movierecs.authservice.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class AppUser {
    private long id;
    private String email;

    public AppUser(String email) {
        this.email = email;
    }
}
