package online.ptsports.PTSports.Controller.AdminApi;



import online.ptsports.PTSports.DTO.ProductDto;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.Service.CatalogService;
import online.ptsports.PTSports.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin

public class ProductAdminController {

    @Autowired
    CatalogService catalogService;

    @Autowired
    ProductService productService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAll(){
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable("id")Integer id){
        return  ResponseEntity.ok(productService.getProductById(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/")
    public ResponseEntity<ProductDto>createGeneral(@ModelAttribute ProductDto productDto)throws IOException{
//    return ResponseEntity.ok(generalProductService.createGeneral(generalProductDto));

        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto>updateGeneral(@ModelAttribute ProductDto productDto, @PathVariable("id")Integer id)throws IOException{
        return  ResponseEntity.ok(productService.updateProduct(productDto, id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<?>deleteGeneral(@PathVariable("id")Integer id){
        productService.deleteProduct(id);
        return new ResponseEntity(new ApiResponse("General delete success!!!", true), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/count")
    public ResponseEntity<Integer> countProduct(){
        return ResponseEntity.ok(productService.count());
    }

}
