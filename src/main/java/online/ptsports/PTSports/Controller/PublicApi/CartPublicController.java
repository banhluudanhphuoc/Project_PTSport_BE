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
@CrossOrigin
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
//        HttpSession httpSession = request.getSession();
//        CartDto cart = null;
//
//        if (httpSession.getAttribute("cart") != null) {
//            cart = (CartDto) httpSession.getAttribute("cart");
//        } else {
//            cart = new CartDto();
//            httpSession.setAttribute("cart", cart);
//        }
//
//        List<CartItemDto> itemList = cart.getItemList();
//
//        Product product = productRepo.findById(cartItem.getProductID()).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", cartItem.getProductID()));
//        ProductDto productDto = productService.convertToProductDto(product);
//
//        Color color = colorRepo.findById(cartItem.getColorID()).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", cartItem.getColorID()));
//        Size size = sizeRepo.findById(cartItem.getSizeID()).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", cartItem.getSizeID()));
//
//        cartItem.setColor(colorService.convertToColorDto(color));
//        cartItem.setSize(sizeService.convertToSizeDto(size));
//
//        cartItem.setProductName(productDto.getName());
//        cartItem.setPrice(productDto.getPrice());
//        cartItem.setImage(productDto.getListImage().get(0).getTitle());
//
//
//        int count = 0;
//        for (CartItemDto item : itemList) {
//            if (item.getProductID() == cartItem.getProductID()&& item.getColorID() == cartItem.getColorID()&&item.getSizeID() == cartItem.getSizeID()) {
//                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
//                if (item.getQuantity() >= productDto.getTotalQuantity()) {
//                    item.setQuantity(productDto.getTotalQuantity());
//                }
//            } else {
//                count++;
//            }
//        }
//        if (count == itemList.size()) {
//            if(cartItem.getQuantity() >= productDto.getTotalQuantity()){
//                cartItem.setQuantity(productDto.getTotalQuantity());
//            }
//            itemList.add(cartItem);
//        }
//        for (CartItemDto item : itemList) {
//            item.setTotalPrice(item.getPrice() * item.getQuantity());
//        }
//        cart.setItemList(itemList);
//
//        httpSession.setAttribute("cart", cart);
//        return ResponseEntity.ok(cart);
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
//        CartDto cart = (CartDto) request.getSession().getAttribute("cart");
//        List<CartItemDto> itemList = cart.getItemList();
//
//        Product product = productRepo.findById(cartItem.getProductID()).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", cartItem.getProductID()));
//        ProductDto productDto = productService.convertToProductDto(product);
//
////        Color color = colorRepo.findById(cartItem.getColorID()).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", cartItem.getColorID()));
////        Size size = sizeRepo.findById(cartItem.getSizeID()).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", cartItem.getSizeID()));
//
//
//
//        for (CartItemDto item : itemList) {
//
//            if ((item.getProductID() == cartItem.getProductID()) && (item.getColorID() == cartItem.getColorID()) && (item.getSizeID() == cartItem.getSizeID())) {
//
//                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
//                cartItem.setColor(item.getColor());
//                cartItem.setProductName(item.getProductName());
//                cartItem.setSize(item.getSize());
//                cartItem.setPrice(item.getPrice());
//                cartItem.setImage(item.getImage());
//                if (item.getQuantity() >= productDto.getTotalQuantity()) {
//                    item.setQuantity(productDto.getTotalQuantity());
//                }
//            }
//            else{
//                continue;
//            }
//            }
//
//        cartItem.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity());
//
//        for (int i = 0; i < itemList.size(); i++) {
//            if ((itemList.get(i).getProductID() == cartItem.getProductID()) && (itemList.get(i).getColorID() == cartItem.getColorID()) && (itemList.get(i).getSizeID() == cartItem.getSizeID())) {
//                itemList.set(i, cartItem);
//            }
//        }
//        cart.setItemList(itemList);
//        request.getSession().setAttribute("cart", cart);

//        return ResponseEntity.ok(cartItem);
        return ResponseEntity.ok(cartService.updateCartItem(userID, id, cartItem));
    }


//    @DeleteMapping("/delete-cart/{userID}")
//    public ResponseEntity<?> deleteCart(@PathVariable("userID") int userID) {
////        CartDto cart = (CartDto) request.getSession().getAttribute("cart");
////        cart = null;
////        request.getSession().setAttribute("cart", cart);
////        return new ResponseEntity<>(new ApiResponse("Delete success", true), HttpStatus.OK);
//        cartService.deleteCart(userID);
//        return new ResponseEntity<>(new ApiResponse("Delete success", true), HttpStatus.OK);
//    }
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
//        CartDto cart = (CartDto) request.getSession().getAttribute("cart");
//        int count = 0;
//        if (cart == null) {
//            count = 0;
//        } else {
//            count = cart.getItemList().size();
//        }
//        return ResponseEntity.ok(count);
        return ResponseEntity.ok(cartService.countItem(userID));
    }

    @GetMapping("/total-price/{userID}")
    public ResponseEntity<Double> getTotalPrice(@PathVariable("userID") int userID) {
//        double total = 0;
//        CartDto cart = (CartDto) request.getSession().getAttribute("cart");
//        if (cart == null || cart.getItemList().size() == 0) {
//            total = 0;
//        } else {
//            List<CartItemDto> cartItems = cart.getItemList();
//
//            for (int i = 0; i < cartItems.size(); i++) {
//                total += cartItems.get(i).getTotalPrice();
//            }
//        }
//
//
//        return ResponseEntity.ok(total);
        return ResponseEntity.ok(cartService.totalPrice(userID));
    }
}
