package online.ptsports.PTSports.Repository;


import online.ptsports.PTSports.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    User findByEmailVerificationToken(String token);
}
