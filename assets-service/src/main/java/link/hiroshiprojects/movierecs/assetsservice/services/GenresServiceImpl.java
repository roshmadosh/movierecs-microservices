package link.hiroshiprojects.movierecs.assetsservice.services;

import link.hiroshiprojects.movierecs.assetsservice.models.Genres;
import link.hiroshiprojects.movierecs.assetsservice.models.GenresObject;
import link.hiroshiprojects.movierecs.assetsservice.repositories.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GenresServiceImpl implements GenresService {
    @Value("azure-blob://datasets/genres.json")
    private Resource blobFile;
    @Value("${TMDB_API_KEY}")
    private String KEY;
    @Value("${source.url}")
    private String HOST;
    private GenreRepository genreRepository;

    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(GenresService.class);

    public GenresServiceImpl(GenreRepository genreRepository, RestTemplate restTemplate) {
        this.genreRepository = genreRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<GenresObject> fetchGenres() {
        String url = String.format("%s/genre/movie/list?api_key=%s", HOST, KEY);
        Genres genres = restTemplate.getForObject(url, Genres.class);

        logger.info("Genres fetched from TMDB API");
        return genres.getGenres();
    }

    @Override
    public List<GenresObject> getGenres() {
        logger.info("Getting genres from database...");
        return genreRepository.findAll();
    }

    @Override
    public void saveGenres(List<GenresObject> genres) {
        logger.info("Saving genres to database...");
        genreRepository.saveAll(genres);
        logger.info("Genres successfully saved to database!");

    }
}
