package link.hiroshiprojects.movierecs.mlservice.services;

import link.hiroshiprojects.movierecs.mlservice.models.GenresObject;
import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;
import link.hiroshiprojects.movierecs.mlservice.repositories.GenresRepository;
import link.hiroshiprojects.movierecs.mlservice.repositories.MovieDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class AssetsServiceDb implements AssetsService {
    private MovieDetailsRepository movieDetailsRepository;
    private GenresRepository genresRepository;
    private final Logger logger = LoggerFactory.getLogger(AssetsServiceDb.class);

    public AssetsServiceDb(MovieDetailsRepository movieDetailsRepository, GenresRepository genresRepository) {
        this.movieDetailsRepository = movieDetailsRepository;
        this.genresRepository = genresRepository;
    }

    @Override
    public List<MovieDetails> getMovieDetails() {
        logger.info("Getting movie details from DB...");
        return movieDetailsRepository.findAll();
    }

    @Override
    public List<GenresObject> getGenres() {
        logger.info("Getting genres from DB...");
        return genresRepository.findAll();
    }

    @Override
    public MovieDetails getMovieById(long id) {
        logger.info("Getting movie by id from DB...");
        return movieDetailsRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Movie with ID " + id + "not found."));
    }
}
