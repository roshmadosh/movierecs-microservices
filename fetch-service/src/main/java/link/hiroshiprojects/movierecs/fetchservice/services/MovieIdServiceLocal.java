package link.hiroshiprojects.movierecs.fetchservice.services;

import link.hiroshiprojects.movierecs.fetchservice.utils.MovieIdUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class MovieIdServiceLocal implements MovieIdService {

    private final Logger logger = LoggerFactory.getLogger(MovieIdServiceLocal.class);
    private MovieIdUtility movieIdUtility;

    public MovieIdServiceLocal(MovieIdUtility movieIdUtility) {
        this.movieIdUtility = movieIdUtility;
    }

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
    public List<Long> getIds(long count) {
        Path path = Paths.get("static/movieids.json");
        movieIdUtility.setFile(path.toFile());

        return movieIdUtility.getIds(count);
    }
}
