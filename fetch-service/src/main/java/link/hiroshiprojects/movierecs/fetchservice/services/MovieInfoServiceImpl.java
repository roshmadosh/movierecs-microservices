package link.hiroshiprojects.movierecs.fetchservice.services;

import link.hiroshiprojects.movierecs.fetchservice.models.MovieIdInfo;
import link.hiroshiprojects.movierecs.fetchservice.models.MovieInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MovieInfoServiceImpl implements MovieInfoService {
    @Value("${source.url}")
    private String HOST;
    @Value("${TMDB_API_KEY}")
    private String API_KEY;
    private RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(MovieInfoServiceImpl.class);

    public MovieInfoServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public MovieInfo getMovieDetails(long movieId) {
        logger.info("Fetching movie details for movie ID " + movieId + "...");
        String url = String.format("%s/movie/%s?api_key=%s", HOST, movieId, API_KEY);

        return restTemplate.getForObject(url, MovieInfo.class);
    }

    @Override
    public void saveMovieDetails(MovieInfo movieInfo) {

    }
}
