package online.ptsports.PTSports.Controller.PublicApi;



import online.ptsports.PTSports.Config.AppConstants;
import online.ptsports.PTSports.DTO.PageDto;
import online.ptsports.PTSports.DTO.ProductDto;
import online.ptsports.PTSports.DTO.SearchDto;
import online.ptsports.PTSports.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/public/")
@CrossOrigin(origins = "https://ptsports.online")
public class ProductPublicController {
    @Autowired
    private ProductService productService;


    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchProduct(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductByName(name));
    }

    @GetMapping("/products")
    public ResponseEntity<PageDto<ProductDto>> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        PageDto<ProductDto> productResponse = productService.getProductsHomePage(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<PageDto<ProductDto>>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable("id")Integer id){
        return  ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping()
    public ResponseEntity<List<ProductDto>> search(@ModelAttribute SearchDto searchDto) {
        return ResponseEntity.ok(productService.search(searchDto));
    }
     @GetMapping("/filter-by-length/{id}")
    public ResponseEntity<List<ProductDto>>filterByLength(@PathVariable("id")int id){
        return ResponseEntity.ok(productService.filterByLength(id));
     }

    @GetMapping("/filter-by-catalog/{id}")
    public ResponseEntity<List<ProductDto>>filterByCatalog(@PathVariable("id")int id){
        return ResponseEntity.ok(productService.filterByCatalog(id));
    }

    @GetMapping("/filter-by-category/{id}")
    public ResponseEntity<List<ProductDto>>filterByCategory(@PathVariable("id")int id){
        return ResponseEntity.ok(productService.filterByCategory(id));
    }

    @GetMapping("/products/latest")
    public List<ProductDto> getLatestProducts() {
        List<ProductDto> latestProducts = productService.getLatestProducts(8);
        return latestProducts;
    }

    @GetMapping("/products/discounts")
    public List<ProductDto> getDiscountedProducts() {
        List<ProductDto> discountedProducts = productService.getDiscountedProducts(9);
        return discountedProducts;
    }


}
