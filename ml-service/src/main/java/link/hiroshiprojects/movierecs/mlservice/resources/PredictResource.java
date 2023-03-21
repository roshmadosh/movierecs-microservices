package link.hiroshiprojects.movierecs.mlservice.resources;

import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;
import link.hiroshiprojects.movierecs.mlservice.models.Prediction;
import link.hiroshiprojects.movierecs.mlservice.services.PredictService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ml/predict")
public class PredictResource {
    private PredictService predictService;

    public PredictResource(PredictService predictService) {
        this.predictService = predictService;
    }

    @PostMapping
    public MovieDetails[] getPrediction(@RequestBody Prediction prediction) {
        return predictService.getPredictions(prediction);
    }
}
