package link.hiroshiprojects.movierecs.fetchservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import link.hiroshiprojects.movierecs.fetchservice.models.MovieIdInfo;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieIdUtility {
    private ObjectMapper mapper;
    private File file;

    public MovieIdUtility(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public List<Long> getIds(long count) {
        if (file == null) {
            throw new IllegalStateException("File must be set using .setFile() before calling .getIds()");
        }
        List<Long> movieIdInfos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

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
