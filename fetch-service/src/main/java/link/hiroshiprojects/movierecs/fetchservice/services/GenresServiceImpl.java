package link.hiroshiprojects.movierecs.fetchservice.services;

import link.hiroshiprojects.movierecs.fetchservice.models.Genres;
import link.hiroshiprojects.movierecs.fetchservice.models.GenresObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GenresServiceImpl implements GenresService {
    @Value("${TMDB_API_KEY}")
    private String KEY;
    @Value("${source.url}")
    private String HOST;

    private RestTemplate restTemplate;

    public GenresServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<GenresObject> fetchGenres() {
        String url = String.format("%s/genre/movie/list?api_key=%s", HOST, KEY);
        Genres genres = restTemplate.getForObject(url, Genres.class);

        return genres.getGenres();
    }
}
