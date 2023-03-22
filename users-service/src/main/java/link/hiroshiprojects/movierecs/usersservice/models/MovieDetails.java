package link.hiroshiprojects.movierecs.usersservice.models;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany(mappedBy = "favorites")
    private List<User> usersFavorited;
}




