package link.hiroshiprojects.movierecs.fetchservice.resources;

import link.hiroshiprojects.movierecs.fetchservice.models.MovieInfo;
import link.hiroshiprojects.movierecs.fetchservice.services.MovieIdService;
import link.hiroshiprojects.movierecs.fetchservice.services.MovieInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/fetch/movie-info", produces = "application/json")
public class MovieInfoResource {

    @Value("${source.url}")
    String url;

    MovieIdService idService;
    MovieInfoService movieInfoService;

    public MovieInfoResource(MovieIdService idService, MovieInfoService movieInfoService) {
        this.idService = idService;
        this.movieInfoService = movieInfoService;
    }

    @GetMapping
    public List<MovieInfo> getMovieInfo(@RequestParam(value = "count") long count,
                                        @RequestParam(value = "save", required = false) boolean save) {
        List<Long> ids = idService.getIds(count);
        List<MovieInfo> infos = movieInfoService.getMovieDetails(ids);

        if (save) {
           movieInfoService.saveMovieDetails(infos);
        }

        return infos;
    }
}
