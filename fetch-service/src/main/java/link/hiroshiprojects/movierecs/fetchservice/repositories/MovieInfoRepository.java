package link.hiroshiprojects.movierecs.fetchservice.repositories;

import link.hiroshiprojects.movierecs.fetchservice.models.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieInfoRepository extends JpaRepository<MovieInfo, Long> {
}
