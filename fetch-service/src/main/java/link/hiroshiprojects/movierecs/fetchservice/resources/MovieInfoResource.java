package link.hiroshiprojects.movierecs.fetchservice.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fetch/movie-info")
public class MovieInfoResource {

    @Value("${source.url}")
    String url;

    @GetMapping
    public void getMovieInfo(@RequestParam(value = "count", required = false) int count) {

    }
}
