package link.hiroshiprojects.movierecs.fetchservice.services;

import link.hiroshiprojects.movierecs.fetchservice.utils.MovieIdUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
@Primary
public class MovieIdServiceAzure implements MovieIdService {
    @Value("azure-blob://datasets/movieids.json.gz")
    private Resource blobFile;
    private MovieIdUtility movieIdUtility;

    public MovieIdServiceAzure(MovieIdUtility movieIdUtility) {
        this.movieIdUtility = movieIdUtility;
    }

    @Override
    public void save(File file, Path path) {
        try (OutputStream os = ((WritableResource) blobFile).getOutputStream()) {
            os.write(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> getIds(long count) {
        try (GZIPInputStream gis = new GZIPInputStream(blobFile.getInputStream())) {
            // convert blob to File
            Path path = Files.createTempFile("blob", ".json");
            FileOutputStream fos = new FileOutputStream(path.toFile());
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) > 0) {
               fos.write(buffer, 0, len);
            }
            fos.close();

            movieIdUtility.setFile(path.toFile());
            return movieIdUtility.getIds(count);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
