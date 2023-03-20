package link.hiroshiprojects.movierecs.fetchservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString
public class MovieInfo {
    private static final String CSV_DELIMTIER = ",";
    private long id;
    private String title;
    private String overview;
    private List<GenresObject> genres;
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
        List<Long> genreIds = genres.stream().map(obj -> obj.getId()).collect(Collectors.toList());
        return id + CSV_DELIMTIER +
                title.replaceAll(",", "") + CSV_DELIMTIER +
                overview.replaceAll(",", "")
                        .replaceAll("\"", "\'")
                        .replaceAll("\r", "<newline>")
                        .replaceAll("\\r", "<newline>") + CSV_DELIMTIER +
                genreIds + CSV_DELIMTIER +
                popularity + CSV_DELIMTIER +
                posterPath;
    }

    // for marshalling the genres attribute
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("title", title);
        obj.put("overview", overview);
        obj.put("genres", genres.stream().map(genre -> {
            JSONObject genreJson = new JSONObject();
            genreJson.put("id", genre.getId());
            genreJson.put("name", genre.getName());
            return genreJson;
        }).collect(Collectors.toList()));
        obj.put("popularity", popularity);
        obj.put("posterPath", posterPath);

        return obj;
    }

}
