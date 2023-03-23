package link.hiroshiprojects.movierecs.assetsservice.repositories;

import link.hiroshiprojects.movierecs.assetsservice.models.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieInfoRepository extends JpaRepository<MovieInfo, Long> {
}
