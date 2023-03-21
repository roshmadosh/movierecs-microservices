package link.hiroshiprojects.movierecs.mlservice.services;


import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;
import link.hiroshiprojects.movierecs.mlservice.models.Prediction;
import java.util.List;

public interface PredictService {
    MovieDetails[] getPredictions(Prediction prediction);
}
