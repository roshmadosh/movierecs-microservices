package link.hiroshiprojects.movierecs.usersservice.repositories;

import link.hiroshiprojects.movierecs.usersservice.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
}

