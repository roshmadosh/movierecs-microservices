package link.hiroshiprojects.movierecs.usersservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "app_users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "usersFavorited")
    private List<MovieId> favorites = new LinkedList<>();

    public void addMovieIdsToFavorites(List<MovieId> movieIds) {
        movieIds.stream().forEach(movie -> movie.getUsersFavorited().add(this));
        favorites.addAll(movieIds);
    }
}
