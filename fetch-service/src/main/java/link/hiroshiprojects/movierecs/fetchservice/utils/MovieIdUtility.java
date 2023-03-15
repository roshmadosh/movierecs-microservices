package link.hiroshiprojects.movierecs.fetchservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import link.hiroshiprojects.movierecs.fetchservice.models.MovieIdInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MovieIdUtility {
    private File file;

    public MovieIdUtility(File file) {
        this.file = file;
    }

    public List<Long> getIds(long count) {
        List<Long> movieIdInfos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            // map each JSON to a MovieIdInfo instance
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                Long id = mapper.readValue(line, MovieIdInfo.class).getId();
                movieIdInfos.add(id);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return movieIdInfos;
    }
}
