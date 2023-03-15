package link.hiroshiprojects.movierecs.fetchservice.services;

import link.hiroshiprojects.movierecs.fetchservice.models.GenresObject;

import java.util.List;

public interface GenresService {

    public List<GenresObject> fetchGenres();

}
