package link.hiroshiprojects.movierecs.mlservice.repositories;

import link.hiroshiprojects.movierecs.mlservice.models.MovieDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDetailsRepository extends ReadOnlyRepository<MovieDetails, Long> {

}
