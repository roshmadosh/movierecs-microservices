package link.hiroshiprojects.movierecs.usersservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "movie_details")
@Getter @Setter @ToString
@NoArgsConstructor
public class MovieId {
    @Id
    private long id;
    @ManyToMany
    @JsonIgnore
    private List<AppUser> usersFavorited = new LinkedList<>();

}




