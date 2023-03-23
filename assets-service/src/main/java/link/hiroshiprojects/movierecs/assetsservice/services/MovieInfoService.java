package link.hiroshiprojects.movierecs.assetsservice.services;

import link.hiroshiprojects.movierecs.assetsservice.models.MovieInfo;

import java.util.List;

public interface MovieInfoService {
    public List<MovieInfo> getMovieDetails(List<Long> movieIds);

    public List<MovieInfo> getAll();
    public MovieInfo getById(long movieId);

    public void saveMovieDetails(List<MovieInfo> movieInfo);

}
