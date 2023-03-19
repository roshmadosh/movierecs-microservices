package link.hiroshiprojects.movierecs.fetchservice.services;

import link.hiroshiprojects.movierecs.fetchservice.models.MovieIdInfo;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface MovieIdService {
    public List<Long> getIds(long count);

}
