package link.hiroshiprojects.movierecs.fetchservice.services;

import link.hiroshiprojects.movierecs.fetchservice.utils.MovieIdUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class MovieIdServiceAzure implements MovieIdService {
    private final Logger logger = LoggerFactory.getLogger(MovieIdServiceAzure.class);
    @Value("${ids.url}")
    private String url;
    @Autowired
    private RestTemplate restTemplate;
    private MovieIdUtility movieIdUtility;

    public MovieIdServiceAzure(MovieIdUtility movieIdUtility) {
        this.movieIdUtility = movieIdUtility;
    }

    @Override
    public List<Long> getIds(long count) {
        logger.info("Fetching IDs from TMDB API...");
        return restTemplate.execute(url, HttpMethod.GET, null, clientResponse -> {
            logger.info("Attempting to unzip file...");
            try (GZIPInputStream gis = new GZIPInputStream(clientResponse.getBody())) {
                Path path = Files.createTempFile("blob", ".json");
                FileOutputStream fos = new FileOutputStream(path.toFile());
                byte[] buffer = new byte[1024];
                int len;
                logger.info("Writing buffer to temp file...");
                while ((len = gis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();

                logger.info("Attempting to read IDs from unzipped file...");
                movieIdUtility.setFile(path.toFile());
                return movieIdUtility.readIds(count);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
