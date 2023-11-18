package online.ptsports.PTSports.Repository;


import online.ptsports.PTSports.Entity.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LengthRepo extends JpaRepository<Length,Integer> {
}
