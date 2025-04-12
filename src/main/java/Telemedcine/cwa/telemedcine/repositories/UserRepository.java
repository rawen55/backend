package Telemedcine.cwa.telemedcine.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Telemedcine.cwa.telemedcine.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    org.apache.catalina.User save(org.apache.catalina.User user);
}
