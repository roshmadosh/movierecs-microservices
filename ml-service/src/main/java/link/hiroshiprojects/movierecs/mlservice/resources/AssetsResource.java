package link.hiroshiprojects.movierecs.mlservice.resources;

import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;
import link.hiroshiprojects.movierecs.mlservice.services.AssetsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/ml/assets")
public class AssetsResource {

    private final AssetsService assetsService;

    public AssetsResource(AssetsService assetsService) {
        this.assetsService = assetsService;
    }

    @GetMapping
    public List<MovieDetails> getMovieDetails(@RequestParam int count) {
        return assetsService.getMovieDetails(count);
    }
}
