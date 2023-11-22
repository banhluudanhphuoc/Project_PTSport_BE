package online.ptsports.PTSports.Controller.PublicApi;

import online.ptsports.PTSports.DTO.CatalogDto;
import online.ptsports.PTSports.Service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/catalogs")
@CrossOrigin
public class CatalogPublicController {
    @Autowired
    CatalogService catalogService;


    @GetMapping()
    public ResponseEntity<List<CatalogDto>> getAll() {
        return ResponseEntity.ok(catalogService.getAllCatalogs());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CatalogDto> getCatalog(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(catalogService.getCatalogById(id));
    }
}
