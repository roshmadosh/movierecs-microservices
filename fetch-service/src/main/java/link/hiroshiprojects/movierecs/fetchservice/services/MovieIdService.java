package link.hiroshiprojects.movierecs.fetchservice.services;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface MovieIdService {

    public void save(File file, Path path);

    public List<Long> getIds(long count);

}
