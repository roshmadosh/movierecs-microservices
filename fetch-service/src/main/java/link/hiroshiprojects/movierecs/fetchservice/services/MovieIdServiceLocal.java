package link.hiroshiprojects.movierecs.fetchservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import link.hiroshiprojects.movierecs.fetchservice.models.MovieIdInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class MovieIdServiceLocal implements MovieIdService {

    private final Logger logger = LoggerFactory.getLogger(MovieIdServiceLocal.class);

    @Override
    public void save(File file, Path dest) {

        logger.info("Extracting gzipped file and saving to " + dest + "...");
        try (GZIPInputStream gis = new GZIPInputStream(new FileInputStream(file));
             FileOutputStream fos = new FileOutputStream(dest.toFile())) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            logger.info("IDs saved successfully to " + dest);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("File could not be saved");
        }

    }

    @Override
    public List<Long> getIds(long count, Path location) {
        List<Long> movieIdInfos = new ArrayList<>();
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(location.toFile()))) {

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
