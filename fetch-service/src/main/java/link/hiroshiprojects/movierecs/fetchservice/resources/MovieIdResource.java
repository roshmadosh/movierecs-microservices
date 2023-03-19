package link.hiroshiprojects.movierecs.fetchservice.resources;

import link.hiroshiprojects.movierecs.fetchservice.services.MovieIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "api/v1/fetch/movie-id", produces = "application/json")
public class MovieIdResource {
    private final Logger logger = LoggerFactory.getLogger(MovieIdResource.class);

    @Autowired
    private MovieIdService idService;

    @GetMapping
    public List<Long> getIds(@RequestParam int count) {
        try {
            List<Long> ids = idService.getIds(count);
            logger.info("ID's fetched successfully. Count: " + count);
            return ids;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
