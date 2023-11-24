package online.ptsports.PTSports.Controller.PublicApi;




import online.ptsports.PTSports.DTO.WishListDto;
import online.ptsports.PTSports.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/wish-list")
@CrossOrigin
public class WishListConrtoller {
   @Autowired
   WishListService wishListService;
    @GetMapping("/{userID}")
    public ResponseEntity<WishListDto> getWish(@PathVariable("userID")Integer userID) {
//        HttpSession httpSession = request.getSession();
//        WishListDto wishList = null;
//
//        if (httpSession.getAttribute("wishList") != null) {
//            wishList = (WishListDto) httpSession.getAttribute("wishList");
//        } else {
//            wishList = new WishListDto();
//            httpSession.setAttribute("wishList", wishList);
//        }
//        return ResponseEntity.ok(wishList);
        return ResponseEntity.ok(wishListService.getWishListByUser(userID));
    }

    @GetMapping("/{userID}/{id}")
    public ResponseEntity<WishListDto> addToWishList(@PathVariable("userID")Integer userID, @PathVariable("id")Integer id) {

//        HttpSession httpSession = request.getSession();
//        WishListDto wishList = null;
//
//        if (httpSession.getAttribute("wishList") != null) {
//            wishList = (WishListDto) httpSession.getAttribute("wishList");
//        } else {
//            wishList = new WishListDto();
//            httpSession.setAttribute("wishList", wishList);
//        }
//        Product product = productRepo.findById(id).orElseThrow(() -> new ResoureNotFoundException("Product", "ID",id));
//        ProductDto productDto = productService.convertToProductDto(product);
//
//
//        List<ProductDto> productDtoListList = wishList.getWishList();
////        if(productDtoListList.contains(productDto)){
////            System.out.println("---------------------");
////            productDtoListList.remove(productDto);
////        }
////        else {
////            System.out.println("....................");
////            productDtoListList.add(productDto);
////        }
//        int count = 0;
//        for (int i = 0; i < productDtoListList.size(); i++) {
//            if(productDtoListList.get(i).getId() != productDto.getId()){
//                count++;
//            }
//        }
//        if(count == productDtoListList.size()){
//            productDtoListList.add(productDto);
//        }
//        else {
////            int size = ;
//            for (int i = 0; i < productDtoListList.size(); i++) {
//                if(productDtoListList.get(i).getId() == productDto.getId()){
//                    productDtoListList.remove(i);
//                }
//
//            }
//            productDtoListList.remove(productDto);
//        }
//
//
//        wishList.setWishList(productDtoListList);
//
//        httpSession.setAttribute("wishList", wishList);
//        return ResponseEntity.ok(wishList);
        return ResponseEntity.ok(wishListService.addToWishList(userID,id));
    }

    @DeleteMapping("/{userID}/{productID}")
    public ResponseEntity<WishListDto> removeFromWishList(
            @PathVariable("userID") Integer userID,
            @PathVariable("productID") Integer productID) {
        return ResponseEntity.ok(wishListService.removeFromWishList(userID, productID));
    }
}
