package online.ptsports.PTSports.Repository;



import online.ptsports.PTSports.Entity.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityRepo extends JpaRepository<Quantity, Integer> {
}
