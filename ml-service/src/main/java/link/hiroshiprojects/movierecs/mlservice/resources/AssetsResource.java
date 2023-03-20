package link.hiroshiprojects.movierecs.mlservice.resources;

import link.hiroshiprojects.movierecs.mlservice.models.GenresObject;
import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;
import link.hiroshiprojects.movierecs.mlservice.services.AssetsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        if (count > 20_000) {
            throw new RuntimeException("Only 20,000 movies available.");
        }
        return assetsService.getMovieDetails(count);
    }

    @GetMapping("/genres")
    public List<GenresObject> getMovieGenres() {
        return assetsService.getGenres();
    }
}
