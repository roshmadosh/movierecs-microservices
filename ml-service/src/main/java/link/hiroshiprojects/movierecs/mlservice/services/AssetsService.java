package link.hiroshiprojects.movierecs.mlservice.services;

import link.hiroshiprojects.movierecs.mlservice.models.GenresObject;
import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;

import java.util.List;

public interface AssetsService {

    public List<MovieDetails> getMovieDetails();

    public List<GenresObject> getGenres();

    public MovieDetails getMovieById(long id);
}
