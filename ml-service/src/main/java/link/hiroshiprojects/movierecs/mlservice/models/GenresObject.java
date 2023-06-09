package link.hiroshiprojects.movierecs.mlservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
