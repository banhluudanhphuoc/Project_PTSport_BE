package online.ptsports.PTSports.Controller.AdminApi;



import online.ptsports.PTSports.DTO.CategoryDto;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.Entity.Category;
import online.ptsports.PTSports.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@CrossOrigin
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAllCategorys());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCatalog(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<CategoryDto> createCatalog(@RequestBody CategoryDto catalogDto) {
        return ResponseEntity.ok(categoryService.createCategory(catalogDto));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCatalog(@PathVariable("id") Integer id, @RequestBody CategoryDto catalogDto) {
        return ResponseEntity.ok(categoryService.updateCategory(catalogDto, id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCatalog(@PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity(new ApiResponse("Category deleted Successfully", true), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/addcategorys")
    public ResponseEntity<?> addCatalods(@RequestBody List<Category> catalogs) {

        // TODO: Xử lý logic để thêm nhiều catalog vào trong cơ sở dữ liệu
        categoryService.addCategorysService(catalogs);
        // Trả về thông báo thành công nếu thêm thành công
        return new ResponseEntity(new ApiResponse("Add all success!!!", true), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletecategorys")
    public ResponseEntity<?> deleteCatalogs(@RequestBody List<Integer> caterotyIds) {
        categoryService.deleteCategorysService(caterotyIds);
        return new ResponseEntity(new ApiResponse("Delete all success!!!", true), HttpStatus.OK);
    }
}