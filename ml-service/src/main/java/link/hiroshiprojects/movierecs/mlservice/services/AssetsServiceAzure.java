package link.hiroshiprojects.movierecs.mlservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import link.hiroshiprojects.movierecs.mlservice.models.Genres;
import link.hiroshiprojects.movierecs.mlservice.models.GenresObject;
import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetsServiceAzure implements AssetsService {
    private final Logger logger = LoggerFactory.getLogger(AssetsServiceAzure.class);
    @Value("azure-blob://datasets/details.json")
    private Resource detailsBlob;
    @Value("azure-blob://datasets/genres.json")
    private Resource genresBlob;
    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<MovieDetails> getMovieDetails(int count) {
//        List<MovieDetails> details = new LinkedList<>();
//        CsvMapper mapper = new CsvMapper();
//        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
//        CsvSchema schema = CsvSchema.builder()
//                .addColumn("id")
//                .addColumn("title")
//                .addColumn("overview")
//                .addColumn("genres")
//                .addColumn("popularity")
//                .addColumn("posterPath")
//                .build();
        List<MovieDetails> results = new LinkedList<>();
        JSONParser parser = new JSONParser();
        // map fetched csv to object, add to results list
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(detailsBlob.getInputStream()))) {
            Object obj = parser.parse(reader);
            JSONArray movieList = (JSONArray) obj;

            for (Object movie: movieList) {
               results.add(mapper.readValue(movie.toString(), MovieDetails.class));
            }
            logger.info(results.get(0).toString());
            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GenresObject> getGenres() {
       logger.info("Fetching genres...");
       try {
           String genresString = StreamUtils.copyToString(
                   genresBlob.getInputStream(),
                   Charset.defaultCharset());

           Genres genres = mapper.readValue(genresString,
                   new TypeReference<>() {});

           logger.info("Genres successfully fetched!");
           return genres.getGenres();
       } catch(IOException e) {
           e.printStackTrace();
       }
       return null;
    }
}
