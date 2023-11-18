package online.ptsports.PTSports.Repository;

import online.ptsports.PTSports.Entity.SocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialLoginRepo extends JpaRepository<SocialLogin, Integer> {
}
