package link.hiroshiprojects.movierecs.fetchservice.resources;

import link.hiroshiprojects.movierecs.fetchservice.services.MovieIdService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/fetch/movie-info", produces = "application/json")
public class MovieInfoResource {

    @Value("${source.url}")
    String url;

    MovieIdService idService;

    public MovieInfoResource(MovieIdService idService) {
        this.idService = idService;
    }

    @GetMapping
    public List<Long> getMovieInfo(@RequestParam(value = "count", required = false) long count) {
        List<Long> ids = idService.getIds(count);
        return ids;
    }
}
