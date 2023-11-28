package online.ptsports.PTSports.Repository;

import online.ptsports.PTSports.Entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepo extends JpaRepository<OrderStatus, Integer> {
}
