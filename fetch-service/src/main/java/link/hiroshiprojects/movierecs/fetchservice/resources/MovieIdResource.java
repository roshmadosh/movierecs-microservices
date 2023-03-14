package link.hiroshiprojects.movierecs.fetchservice.resources;

import link.hiroshiprojects.movierecs.fetchservice.services.MovieIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("api/v1/fetch/movie-id")
public class MovieIdResource {
    @Value("${ids.url}")
    private String url;

    private RestTemplate restTemplate;
    private MovieIdService idService;
    private final Logger logger = LoggerFactory.getLogger(MovieIdResource.class);

    public MovieIdResource(RestTemplate restTemplate, MovieIdService idService) {
        this.restTemplate = restTemplate;
        this.idService = idService;
    }

    @GetMapping
    public ResponseEntity<String> getIds() {
        logger.info("Fetching IDs from " + url + "...");

        File file = restTemplate.execute(url, HttpMethod.GET, null, clientResponse -> {
           File ret = File.createTempFile("download", "tmp");
           StreamUtils.copy(clientResponse.getBody(), new FileOutputStream(ret));
           return ret;
        });

        Path path = Paths.get("static/movieids.json");

        try {
            idService.save(file, path);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to save file.");
        }

        return ResponseEntity.ok("Saved file successfully!");
    }

}
