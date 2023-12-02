package online.ptsports.PTSports.Repository;

import online.ptsports.PTSports.Entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRepo extends JpaRepository<Discount, Integer> {

}

