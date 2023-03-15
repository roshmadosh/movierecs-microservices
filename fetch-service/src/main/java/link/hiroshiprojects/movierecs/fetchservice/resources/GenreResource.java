package link.hiroshiprojects.movierecs.fetchservice.resources;

import link.hiroshiprojects.movierecs.fetchservice.models.Genres;
import link.hiroshiprojects.movierecs.fetchservice.models.GenresObject;
import link.hiroshiprojects.movierecs.fetchservice.services.GenresService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/fetch/genres", produces = "application/json")
public class GenreResource {

    private GenresService genreService;

    public GenreResource(GenresService genreService) {
        this.genreService = genreService;
    }

    @GetMapping()
    public ResponseEntity<List<GenresObject>> getGenres() {
        List<GenresObject> genres;
        try {
            genres = genreService.fetchGenres();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Collections.emptyList());
        }

        return ResponseEntity.ok().body(genres);
    }
}
