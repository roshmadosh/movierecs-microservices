package link.hiroshiprojects.movierecs.assetsservice.repositories;

import link.hiroshiprojects.movierecs.assetsservice.models.GenresObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenresObject, Long> {
}
