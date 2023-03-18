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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
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
    public List<MovieInfo> getMovieDetails(List<Long> movieIds) {
        // open a thread pool
        int THREAD_COUNT = 10;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<MovieInfo>> futures = new ArrayList<>();

        logger.info("Fetching movie details...");
        // fetch details for all ids
        for (int i = 0; i < movieIds.size(); i++) {
            long id = movieIds.get(i);

            // write to log for every 100th movie
            if (i % 100 == 0) {
                logger.info("PULSE CHECK: Still fetching. Most recent ID fetched: " + id);
            }

            // dispatch thread
            Callable<MovieInfo> callable = new GetDetailsCallable(id);
            Future<MovieInfo> future = executor.submit(callable);
            futures.add(future);
        }

        // resolve futures
        List<MovieInfo> details = futures.stream().map(future -> {
            try {
                return future.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        logger.info("Fetched movie details successfully!");
        return details;
    }

    @Override
    public void saveMovieDetails(List<MovieInfo> movieInfos) {
        List<String> movieStrings = movieInfos.stream().map(info -> info.genereateCsvString())
                .collect(Collectors.toList());

        logger.info("Initiating creation and persistence of " + "details.csv...");
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(((WritableResource) blobFile).getOutputStream()))) {
//           writer.write(MovieInfo.generateCsvStringHeader());

           for (String row: movieStrings) {
               writer.newLine();
               writer.write(row);
           }

           logger.info("Successfully saved " + "details.csv" + " to Azure!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class GetDetailsCallable implements Callable<MovieInfo> {
        private long movieId;

        GetDetailsCallable(long movieId) {
            this.movieId = movieId;
        }

        @Override
        public MovieInfo call() throws Exception {
            String url = String.format("%s/movie/%s?api_key=%s", HOST, movieId, API_KEY);

            return restTemplate.getForObject(url, MovieInfo.class);
        }
    }

}
