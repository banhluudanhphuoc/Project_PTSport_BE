package online.ptsports.PTSports.Controller.AdminApi;


import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.DTO.SizeDto;
import online.ptsports.PTSports.Entity.Size;
import online.ptsports.PTSports.Service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sizes")
@CrossOrigin
public class SizeAdminController {
    @Autowired
    SizeService sizeService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<SizeDto>> getAllSize(){
        return ResponseEntity.ok(sizeService.getAllSize());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<SizeDto> getOne(@PathVariable("id")Integer id){
        return ResponseEntity.ok(sizeService.getSizeById(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<SizeDto>createColor(@RequestBody SizeDto sizeDto){
        return ResponseEntity.ok(sizeService.createSize(sizeDto));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<SizeDto>updateColor(@PathVariable("id")Integer id, @RequestBody SizeDto sizeDto){
        return ResponseEntity.ok(sizeService.updateSize(sizeDto, id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteColor(@PathVariable("id")Integer id){
        sizeService.deleteSize(id);
        return new ResponseEntity(new ApiResponse("Delete success!!!", true), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/addSizes")
    public ResponseEntity<?>  addSizes(@RequestBody List<Size> sizes) {

        // TODO: Xử lý logic để thêm nhiều màu sắc vào trong cơ sở dữ liệu
        sizeService.addSizesService(sizes);
        // Trả về thông báo thành công nếu thêm thành công
        return new ResponseEntity(new ApiResponse("Add all success!!!", true), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteSizes")
    public ResponseEntity<?> deleteSizes(@RequestBody List<Integer> sizeIds) {
        sizeService.deleteSizesService(sizeIds);
        return new ResponseEntity(new ApiResponse("Delete all success!!!", true), HttpStatus.OK);
    }
}
