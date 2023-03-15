package link.hiroshiprojects.movierecs.fetchservice.resources;

import link.hiroshiprojects.movierecs.fetchservice.services.MovieIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "api/v1/fetch/movie-id", produces = "application/json")
public class MovieIdResource {
    @Value("${ids.url}")
    private String url;

    @Autowired
    private MovieIdService idService;

    @Autowired
    private RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(MovieIdResource.class);

    @GetMapping
    public ResponseEntity<Map<String, Object>> getIds() {
        logger.info("Fetching IDs from " + url + "...");

        File file = restTemplate.execute(url, HttpMethod.GET, null, clientResponse -> {
           File ret = File.createTempFile("download", "tmp");
           StreamUtils.copy(clientResponse.getBody(), new FileOutputStream(ret));
           return ret;
        });


        Path path = Paths.get("static/movieids.json");
        Map<String, Object> respObject = new HashMap<>();
        try {
            idService.save(file, path);
        } catch (Exception e) {
            respObject.put("message", "Failed to save file.");
            return ResponseEntity.internalServerError().body(respObject);
        }

        respObject.put("message", "Saved file successfully!");
        return ResponseEntity.ok().body(respObject);
    }

}
