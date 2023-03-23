package link.hiroshiprojects.movierecs.fetchservice.repositories;

import link.hiroshiprojects.movierecs.fetchservice.models.GenresObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenresObject, Long> {
}
