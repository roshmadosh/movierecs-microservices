package link.hiroshiprojects.movierecs.fetchservice.services;

import link.hiroshiprojects.movierecs.fetchservice.models.MovieInfo;

import java.util.List;

public interface MovieInfoService {
    public List<MovieInfo> getMovieDetails(List<Long> movieIds);

    public void saveMovieDetails(List<MovieInfo> movieInfo);

}
