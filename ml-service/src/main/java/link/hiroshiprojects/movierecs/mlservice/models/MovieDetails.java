package link.hiroshiprojects.movierecs.mlservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "movie_details")
@Getter @Setter @ToString
public class MovieDetails {
    @Id
    private long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String overview;
    @ManyToMany
    private List<GenresObject> genres;
    private double popularity;
    private String posterPath;

}