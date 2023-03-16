package link.hiroshiprojects.movierecs.fetchservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.URI;

@Getter @Setter @ToString
public class MovieInfo {
    private static final String CSV_DELIMTIER = ",";
    private long id;
    private String title;
    private String overview;
    private double  popularity;
    private String posterPath;

    public static String generateCsvStringHeader() {
        return "id" + CSV_DELIMTIER +
                "title" + CSV_DELIMTIER +
                "overview" + CSV_DELIMTIER +
                "popularity" + CSV_DELIMTIER +
                "post_path";
    }

    public String genereateCsvString() {
        return id + CSV_DELIMTIER +
                title.replaceAll(",", "") + CSV_DELIMTIER +
                overview.replaceAll(",", "") + CSV_DELIMTIER +
                popularity + CSV_DELIMTIER +
                posterPath;
    }

}
