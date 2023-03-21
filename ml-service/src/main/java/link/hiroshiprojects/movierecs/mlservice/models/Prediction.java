package link.hiroshiprojects.movierecs.mlservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class Prediction {
    private List<String> titles;
    private int count;
}
