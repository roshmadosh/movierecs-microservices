package link.hiroshiprojects.movierecs.usersservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "app_users")
@Getter
@Setter
@ToString
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String email;

    private Set<Long> favorites = new HashSet<>();

    public void addMovieIdsToFavorites(Collection<Long> movieIds) {
        favorites.addAll(movieIds);
    }
}
