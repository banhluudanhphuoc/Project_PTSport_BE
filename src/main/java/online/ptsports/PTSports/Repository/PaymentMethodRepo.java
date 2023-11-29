package online.ptsports.PTSports.Repository;

import online.ptsports.PTSports.Entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepo extends JpaRepository<PaymentMethod , Integer> {
}
