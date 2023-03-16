package link.hiroshiprojects.movierecs.fetchservice.services;

import link.hiroshiprojects.movierecs.fetchservice.models.MovieInfo;

import java.util.List;

public interface MovieInfoService {
    public MovieInfo getMovieDetails(long movieId);

    public void saveMovieDetails(List<MovieInfo> movieInfo);

}
