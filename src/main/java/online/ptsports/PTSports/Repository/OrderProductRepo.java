package online.ptsports.PTSports.Repository;



import online.ptsports.PTSports.Entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepo extends JpaRepository<OrderProduct, Integer> {

//    @Query("select o.product from OrderProduct o group by o.product.id order by sum (o.quantity) DESC limit 5")
//   @Query(value = "", nativeQuery = true)
//    List<Product> hotSaler();
}
