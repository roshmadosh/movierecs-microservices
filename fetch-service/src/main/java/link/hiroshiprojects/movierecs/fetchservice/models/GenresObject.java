package link.hiroshiprojects.movierecs.fetchservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "genres")
@Getter @Setter @ToString
public class GenresObject {
    @Id
    private long id;
    private String name;
}
