package link.hiroshiprojects.movierecs.mlservice.repositories;

import link.hiroshiprojects.movierecs.mlservice.models.GenresObject;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends ReadOnlyRepository<GenresObject, Long> {
}
