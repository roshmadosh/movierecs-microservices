package link.hiroshiprojects.movierecs.mlservice.services;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class AssetsServiceAzure implements AssetsService {
    @Value("azure-blob://datasets/details.csv")
    private Resource blobFile;
    private final Logger logger = LoggerFactory.getLogger(AssetsServiceAzure.class);
//    private final ObjectMapper mapper;

//    public AssetsServiceAzure(ObjectMapper mapper) {
//        this.mapper = mapper;
//    }

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
        try(MappingIterator<MovieDetails> it = mapper.readerFor(MovieDetails.class)
                .with(schema)
                .readValues(blobFile.getInputStream())) {

           int iter = 0;
           while (it.hasNextValue() && iter < count) {
               MovieDetails value = it.nextValue();
               details.add(value);
               iter++;

               logger.info("Processing " + value.getTitle() + "...");
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return details;
    }
}
