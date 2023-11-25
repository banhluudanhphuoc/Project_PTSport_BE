package online.ptsports.PTSports.Controller.AdminApi;

import online.ptsports.PTSports.DTO.CatalogDto;
import online.ptsports.PTSports.DTO.DiscountDTO;
import online.ptsports.PTSports.Service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/discounts")
public class DiscountAdminController {

    @Autowired
    private DiscountService discountService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<DiscountDTO>> getDiscountsByProductId(@PathVariable Integer productId) {
        List<DiscountDTO> discounts = discountService.getDiscountsByProductId(productId);
        return new ResponseEntity<>(discounts, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<DiscountDTO> addDiscount(@RequestBody DiscountDTO discountDTO) {
        DiscountDTO addedDiscount = discountService.addDiscount(discountDTO);
        return new ResponseEntity<>(addedDiscount, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<DiscountDTO>updateDiscount(@PathVariable("id")Integer id, @RequestBody DiscountDTO discountDTO){
        return ResponseEntity.ok(discountService.updateDiscount(discountDTO, id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{discountId}")
    public ResponseEntity<String> deleteDiscount(@PathVariable Integer discountId) {
        discountService.deleteDiscount(discountId);
        return new ResponseEntity<>("Discount deleted successfully", HttpStatus.OK);
    }
}

