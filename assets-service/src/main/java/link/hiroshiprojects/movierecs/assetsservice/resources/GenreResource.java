package link.hiroshiprojects.movierecs.assetsservice.resources;

import link.hiroshiprojects.movierecs.assetsservice.models.GenresObject;
import link.hiroshiprojects.movierecs.assetsservice.services.GenresService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/assets/genres", produces = "application/json")
public class GenreResource {

    private GenresService genreService;

    public GenreResource(GenresService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenresObject>> getGenres(@RequestParam(required = false) boolean save) {
        try {
            List<GenresObject> genres;
            // fetch from 3rd party API or from native DB
            if (save) {
                genres = genreService.fetchGenres();
                genreService.saveGenres(genres);
            } else {
                genres = genreService.getGenres();
            }
            return ResponseEntity.ok().body(genres);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Collections.emptyList());
        }
    }
}
