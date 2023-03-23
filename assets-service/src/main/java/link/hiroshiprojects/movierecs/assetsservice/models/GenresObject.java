package link.hiroshiprojects.movierecs.assetsservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "genres")
@Getter @Setter @ToString
public class GenresObject {
    @Id
    private long id;
    private String name;
}
