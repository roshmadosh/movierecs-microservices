package link.hiroshiprojects.movierecs.mlservice.services;

import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;
import link.hiroshiprojects.movierecs.mlservice.models.Prediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PredictServiceImpl implements PredictService {
    @Autowired
    private RestTemplate restTemplate;
    private final String url = "http://localhost:8000/recommendations";

    @Override
    public MovieDetails[] getPredictions(Prediction prediction) {

        HttpEntity<Prediction> request = new HttpEntity<>(prediction);
        MovieDetails[] results = restTemplate.postForEntity(url,
               request, MovieDetails[].class).getBody();

        return results;
    }
}
