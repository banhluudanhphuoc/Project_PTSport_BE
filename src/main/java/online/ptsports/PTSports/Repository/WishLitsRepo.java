package online.ptsports.PTSports.Repository;



import online.ptsports.PTSports.Entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishLitsRepo extends JpaRepository<WishList, Integer> {
    WishList getWishListByUserID(Integer id);
}
