package link.hiroshiprojects.movierecs.fetchservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
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
            while((len = gis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            logger.info("IDs saved successfully to " + dest);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("File could not be saved");
        }

    }
}
