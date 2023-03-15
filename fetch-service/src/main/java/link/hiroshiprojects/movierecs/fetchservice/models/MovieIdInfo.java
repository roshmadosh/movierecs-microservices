package link.hiroshiprojects.movierecs.fetchservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovieIdInfo {
    private boolean adult;
    private long id;
    private String originalTitle;
    private double popularity;
    private boolean video;
}