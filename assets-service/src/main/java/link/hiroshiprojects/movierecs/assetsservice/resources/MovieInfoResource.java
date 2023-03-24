package link.hiroshiprojects.movierecs.assetsservice.resources;

import link.hiroshiprojects.movierecs.assetsservice.models.MovieInfo;
import link.hiroshiprojects.movierecs.assetsservice.services.MovieIdService;
import link.hiroshiprojects.movierecs.assetsservice.services.MovieInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/assets/details", produces = "application/json")
public class MovieInfoResource {
    @Value("${source.url}")
    String url;

    MovieIdService idService;
    MovieInfoService movieInfoService;

    public MovieInfoResource(MovieIdService idService, MovieInfoService movieInfoService) {
        this.idService = idService;
        this.movieInfoService = movieInfoService;
    }

    /**
     * Get all movies from database.
     */
    @GetMapping
    public List<MovieInfo> getMoviesFromDb() {
       return movieInfoService.getAll();
    }

    /**
     * Find movie by ID from database.
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<Object> getMovieById(@PathVariable(name = "movieId") long movieId) {
        MovieInfo movie = movieInfoService.getById(movieId);
        if (movie == null) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", "Movie with ID " + movieId + " not found.");
            return ResponseEntity.badRequest().body(resp);
        }
        return ResponseEntity.ok().body(movie);

    }

    /**
     *  For getting movie data from TMDB.
     */
    @GetMapping("/fetch")
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
