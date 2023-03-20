package link.hiroshiprojects.movierecs.mlservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class MovieDetails {
    private long id;
    private String title;
    private String overview;
    private List<GenresObject> genres;
    private double popularity;
    private String posterPath;
}

