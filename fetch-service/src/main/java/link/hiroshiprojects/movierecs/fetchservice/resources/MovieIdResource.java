package link.hiroshiprojects.movierecs.fetchservice.resources;

import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;


@RestController
@RequestMapping("api/v1/fetch/movie-id")
public class MovieIdResource {

    @Value("${ids.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(MovieIdResource.class);

    @GetMapping
    public void getIds() {
        logger.info("Fetching IDs from " + url + "...");

        File file = restTemplate.execute(url, HttpMethod.GET, null, clientResponse -> {
           File ret = File.createTempFile("download", "tmp");
           StreamUtils.copy(clientResponse.getBody(), new FileOutputStream(ret));
           return ret;
        });

        Path dest = Paths.get("static/result.json");

        logger.info("Extracting gzipped file and saving to " + dest + "...");
        try (GZIPInputStream gis = new GZIPInputStream(new FileInputStream(file));
             FileOutputStream fos = new FileOutputStream(dest.toFile())) {

            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            logger.info("IDs saved successfully!");

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
