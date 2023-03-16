package link.hiroshiprojects.movierecs.fetchservice.services;

import link.hiroshiprojects.movierecs.fetchservice.models.MovieInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieInfoServiceImpl implements MovieInfoService {
    @Value("${source.url}")
    private String HOST;
    @Value("${TMDB_API_KEY}")
    private String API_KEY;
    @Value("azure-blob://datasets/details.csv")
    private Resource blobFile;

    private RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(MovieInfoServiceImpl.class);

    public MovieInfoServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public MovieInfo getMovieDetails(long movieId) {
        logger.info("Fetching movie details for movie ID " + movieId + "...");
        String url = String.format("%s/movie/%s?api_key=%s", HOST, movieId, API_KEY);

        return restTemplate.getForObject(url, MovieInfo.class);
    }

    @Override
    public void saveMovieDetails(List<MovieInfo> movieInfos) {
        List<String> movieStrings = movieInfos.stream().map(info -> info.genereateCsvString())
                .collect(Collectors.toList());

        logger.info("Initiating creation and persistence of " + "details.csv..");
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(((WritableResource) blobFile).getOutputStream()))) {
           writer.write(MovieInfo.generateCsvStringHeader());

           for (String row: movieStrings) {
               writer.newLine();
               writer.write(row);
           }

           logger.info("Successfully saved " + "details.csv" + " to Azure!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
