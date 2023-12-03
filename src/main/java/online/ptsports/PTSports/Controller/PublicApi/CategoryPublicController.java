package online.ptsports.PTSports.Controller.PublicApi;


import online.ptsports.PTSports.DTO.CategoryDto;
import online.ptsports.PTSports.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/categories")
@CrossOrigin(origins = "https://ptsports.online")
public class CategoryPublicController {
    @Autowired
    CategoryService categoryService;

    @GetMapping()

    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAllCategorys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
