package online.ptsports.PTSports.Repository;

import online.ptsports.PTSports.Entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepo extends JpaRepository<OrderStatus, Integer> {
}
