package online.ptsports.PTSports.Controller.AdminApi;



import online.ptsports.PTSports.DTO.CatalogDto;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.Entity.Catalog;
import online.ptsports.PTSports.Service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/catalogs")
@CrossOrigin
public class CatalogAdminController {
    @Autowired
    CatalogService catalogService;

   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<CatalogDto>> getAll(){
        return ResponseEntity.ok(catalogService.getAllCatalogs());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CatalogDto>getCatalog(@PathVariable("id") Integer id){
        return ResponseEntity.ok(catalogService.getCatalogById(id));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<CatalogDto>createCatalog(@RequestBody CatalogDto catalogDto){
        return ResponseEntity.ok(catalogService.createCatalog(catalogDto));
    }

   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<CatalogDto>updateCatalog(@PathVariable("id")Integer id, @RequestBody CatalogDto catalogDto){
        return ResponseEntity.ok(catalogService.updateCatalog(catalogDto, id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCatalog(@PathVariable("id")Integer id){
        catalogService.deleteCatalog(id);
        return new ResponseEntity(new ApiResponse("User deleted Successfully",true), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/addCatalogs")
    public ResponseEntity<?>  addCatalods(@RequestBody List<Catalog> catalogs) {

        // TODO: Xử lý logic để thêm nhiều catalog vào trong cơ sở dữ liệu
        catalogService.addCatalogsService(catalogs);
        // Trả về thông báo thành công nếu thêm thành công
        return new ResponseEntity(new ApiResponse("Add all success!!!", true), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteCatalogs")
    public ResponseEntity<?> deleteCatalogs(@RequestBody List<Integer> catalogIds) {
        catalogService.deleteCatalogsService(catalogIds);
        return new ResponseEntity(new ApiResponse("Delete all success!!!", true), HttpStatus.OK);
    }
}
