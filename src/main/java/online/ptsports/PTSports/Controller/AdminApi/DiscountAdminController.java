package online.ptsports.PTSports.Controller.AdminApi;


import online.ptsports.PTSports.DTO.CategoryDto;
import online.ptsports.PTSports.DTO.DiscountDto;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.Entity.Discount;
import online.ptsports.PTSports.Service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin/discounts")
@CrossOrigin(origins = "https://ptsports.online")
public class DiscountAdminController {

    @Autowired
    DiscountService discountService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Discount> addDiscount(@RequestBody DiscountDto discountDTO) {
        Discount newDiscount = discountService.addDiscount(discountDTO);
        return new ResponseEntity(new ApiResponse("Add Discount Success!!!", true), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{discountId}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Integer discountId) {
        discountService.deleteDiscountById(discountId);
        return new ResponseEntity(new ApiResponse("Delete Discount Success!!!", true), HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public ResponseEntity<List<DiscountDto>> getAll() {
        return ResponseEntity.ok(discountService.getAllDiscounts());
    }


}
