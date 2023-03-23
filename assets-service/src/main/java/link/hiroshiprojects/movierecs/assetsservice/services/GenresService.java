package link.hiroshiprojects.movierecs.assetsservice.services;

import link.hiroshiprojects.movierecs.assetsservice.models.GenresObject;

import java.util.List;

public interface GenresService {

    public List<GenresObject> fetchGenres();

    public List<GenresObject> getGenres();
    public void saveGenres(List<GenresObject> genres);

}
