package online.ptsports.PTSports.Controller.PublicApi;




import online.ptsports.PTSports.DTO.WishListDto;
import online.ptsports.PTSports.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/wish-list")
@CrossOrigin(origins = "https://ptsports.online")
public class WishListConrtoller {
   @Autowired
   WishListService wishListService;
    @GetMapping("/{userID}")
    public ResponseEntity<WishListDto> getWish(@PathVariable("userID")Integer userID) {
        return ResponseEntity.ok(wishListService.getWishListByUser(userID));
    }

    @GetMapping("/{userID}/{id}")
    public ResponseEntity<WishListDto> addToWishList(@PathVariable("userID")Integer userID, @PathVariable("id")Integer id) {

        return ResponseEntity.ok(wishListService.addToWishList(userID,id));
    }

    @DeleteMapping("/{userID}/{productID}")
    public ResponseEntity<WishListDto> removeFromWishList(
            @PathVariable("userID") Integer userID,
            @PathVariable("productID") Integer productID) {
        return ResponseEntity.ok(wishListService.removeFromWishList(userID, productID));
    }
}
