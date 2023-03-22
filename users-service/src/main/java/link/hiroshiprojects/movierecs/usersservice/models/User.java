package link.hiroshiprojects.movierecs.usersservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String email;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<MovieDetails> favorites;
}
