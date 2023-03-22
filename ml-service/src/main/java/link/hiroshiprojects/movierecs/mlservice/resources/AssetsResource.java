package link.hiroshiprojects.movierecs.mlservice.resources;

import link.hiroshiprojects.movierecs.mlservice.models.GenresObject;
import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;
import link.hiroshiprojects.movierecs.mlservice.services.AssetsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/ml/assets", produces = "application/json")
public class AssetsResource {

    private final AssetsService assetsService;

    public AssetsResource(AssetsService assetsService) {
        this.assetsService = assetsService;
    }

    @GetMapping("/details")
    public List<MovieDetails> getMovieDetails(@RequestParam int count) {
        if (count > 10_000) {
            throw new RuntimeException("Only 10,000 movies available.");
        }
        return assetsService.getMovieDetails(count);
    }
    @GetMapping("/details/{id}")
    public MovieDetails getMovieById(@PathVariable long id) {
        return assetsService.getMovieById(id);
    }

    @GetMapping("/genres")
    public List<GenresObject> getMovieGenres() {
        return assetsService.getGenres();
    }

}
