package link.hiroshiprojects.movierecs.usersservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString @NoArgsConstructor
public class MovieDetails {
    private long id;
    private String title;
    private String overview;
    private List<GenresObject> genres;
    private double popularity;
    private String posterPath;
}
