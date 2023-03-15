package link.hiroshiprojects.movierecs.fetchservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class Genres {
    private List<GenresObject> genres;
}


