package link.hiroshiprojects.movierecs.mlservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import link.hiroshiprojects.movierecs.mlservice.models.Genres;
import link.hiroshiprojects.movierecs.mlservice.models.GenresObject;
import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

@Service
public class AssetsServiceAzure implements AssetsService {
    private final Logger logger = LoggerFactory.getLogger(AssetsServiceAzure.class);
    @Value("azure-blob://datasets/details.csv")
    private Resource detailsBlob;
    @Value("azure-blob://datasets/genres.json")
    private Resource genresBlob;
    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<MovieDetails> getMovieDetails(int count) {
        List<MovieDetails> details = new LinkedList<>();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("id")
                .addColumn("title")
                .addColumn("overview")
                .addColumn("popularity")
                .addColumn("posterPath")
                .build();

        // map fetched csv to object, add to results list
        try(MappingIterator<MovieDetails> it = mapper.readerFor(MovieDetails.class)
                .with(schema)
                .readValues(detailsBlob.getInputStream())) {

           int iter = 0;
           while (it.hasNextValue() && iter < count) {
               MovieDetails value = it.nextValue();
               details.add(value);
               iter++;

               logger.info("Processing " + value.getTitle() + "...");
           }
           logger.info("Details successfullly retrieved. Count: " + count);
           return details;

        } catch (IOException e) {
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
