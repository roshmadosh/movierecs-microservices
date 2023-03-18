package link.hiroshiprojects.movierecs.mlservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.URI;

@Getter @Setter @ToString
public class MovieDetails {
    private long id;
    private String title;
    private String overview;
    private double  popularity;
    private String posterPath;
}

