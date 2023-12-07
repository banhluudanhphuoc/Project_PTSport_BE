package online.ptsports.PTSports.Controller.PublicApi;



import online.ptsports.PTSports.DTO.CartDto;
import online.ptsports.PTSports.DTO.CartItemDto;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.Entity.Cart;
import online.ptsports.PTSports.Entity.CartItem;
import online.ptsports.PTSports.Repository.CartItemRepo;
import online.ptsports.PTSports.Repository.CartRepo;
import online.ptsports.PTSports.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/public/cart")
@CrossOrigin(origins = "https://ptsports.online")
public class CartPublicController {

    @Autowired
    CartService cartService;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartItemRepo cartItemRepo;

    @GetMapping("/{id}")

    public ResponseEntity<CartDto> getCart(@PathVariable("id") Integer id) {

//        HttpSession httpSession = request.getSession();
//        CartDto cart = null;
//
//        if (httpSession.getAttribute("cart") != null) {
//            cart = (CartDto) httpSession.getAttribute("cart");
//        } else {
//            cart = new CartDto();
//            httpSession.setAttribute("cart", cart);
//        }

//        return ResponseEntity.ok(cart);
        return ResponseEntity.ok(cartService.getCart(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDto> addToCart(@PathVariable("id") Integer id, @RequestBody CartItemDto cartItem,
                                             final HttpServletRequest request, final HttpServletResponse response) {

        return ResponseEntity.ok(cartService.addToCart(id, cartItem));
    }

//    @PutMapping("/delete/{userID}")
//    public ResponseEntity<?> deleteCartItem(@PathVariable("userID") Integer userID, @RequestParam("id") int id, @RequestParam("size") int sizeID, @RequestParam("color") int colorID,
//                                            final HttpServletRequest request, final HttpServletResponse response) {
//
//        cartService.deleteCartItem(userID, id, sizeID, colorID);
//        return new ResponseEntity<>(new ApiResponse("Delete success!!!", true), HttpStatus.OK);
//    }

    @PutMapping("/delete")
    public ResponseEntity<?> deleteCartItem( @RequestParam("id") int id,
                                             final HttpServletRequest request, final HttpServletResponse response) {
        cartItemRepo.deleteById(id);
        return new ResponseEntity<>(new ApiResponse("Delete success!!!", true), HttpStatus.OK);
    }



    @PutMapping("/update/{userID}/{id}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable("userID") int userID,
                                                      @PathVariable("id") int id, @RequestBody CartItemDto cartItem) {
        return ResponseEntity.ok(cartService.updateCartItem(userID, id, cartItem));
    }



@DeleteMapping("/delete-cart/{userID}")
public ResponseEntity<?> deleteCart(@PathVariable("userID") int userID) {
    try {
        // Kiểm tra xem giỏ hàng có tồn tại không
        Cart cart = cartRepo.findCartByUserId(userID);
        if (cart == null) {
            return new ResponseEntity<>(new ApiResponse("Cart not found", false), HttpStatus.NOT_FOUND);
        }

        // Lấy danh sách các mục trong giỏ hàng
        List<CartItem> cartItems = cart.getItemList();

        // Xóa các mục trong giỏ hàng
        cartItems.forEach(cartItem -> {
            cartItem.setCart(null);
            cartItemRepo.delete(cartItem);
        });

        // Xóa giỏ hàng
        cartRepo.delete(cart);

        return new ResponseEntity<>(new ApiResponse("Delete success", true), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse("Error deleting cart", false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @GetMapping("/count/{userID}")
    public ResponseEntity<Integer> countItem(@PathVariable("userID") int userID) {
        return ResponseEntity.ok(cartService.countItem(userID));
    }

    @GetMapping("/total-price/{userID}")
    public ResponseEntity<Double> getTotalPrice(@PathVariable("userID") int userID) {

        return ResponseEntity.ok(cartService.totalPrice(userID));
    }
}
