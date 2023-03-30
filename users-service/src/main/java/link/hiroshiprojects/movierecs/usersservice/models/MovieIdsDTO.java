package link.hiroshiprojects.movierecs.usersservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Getter @Setter @ToString
public class MovieIdsDTO implements Serializable {
    private String email;
    private Set<Long> movieIds;
}
