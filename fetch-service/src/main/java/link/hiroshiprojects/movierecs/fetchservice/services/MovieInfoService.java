package link.hiroshiprojects.movierecs.fetchservice.services;

import link.hiroshiprojects.movierecs.fetchservice.models.MovieIdInfo;
import link.hiroshiprojects.movierecs.fetchservice.models.MovieInfo;

public interface MovieInfoService {
    public MovieInfo getMovieDetails(long movieId);

    public void saveMovieDetails(MovieInfo movieInfo);

}
