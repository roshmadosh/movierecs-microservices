package link.hiroshiprojects.movierecs.fetchservice.resources;

import link.hiroshiprojects.movierecs.fetchservice.models.Genres;
import link.hiroshiprojects.movierecs.fetchservice.models.GenresObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/fetch/genres")
public class GenreResource {

    @Value("${TMDB_API_KEY}")
    private String KEY;
    @Value("${source.url}")
    private String HOST;
    private final RestTemplate restTemplate;

    public GenreResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping()
    public List<GenresObject> getGenres() {
        String url = String.format("%s/genre/movie/list?api_key=%s", HOST, KEY);
        Genres genres = restTemplate.getForObject(url, Genres.class);

        return genres.getGenres();
    }
}
