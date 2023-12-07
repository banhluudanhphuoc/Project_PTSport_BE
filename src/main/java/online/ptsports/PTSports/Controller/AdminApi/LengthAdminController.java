package online.ptsports.PTSports.Controller.AdminApi;



import online.ptsports.PTSports.DTO.LengthDto;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.Entity.Length;
import online.ptsports.PTSports.Service.LengthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/lengths")
@CrossOrigin(origins = "https://ptsports.online")
public class LengthAdminController {
    @Autowired
    LengthService lengthService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<LengthDto>> getAll(){
        return ResponseEntity.ok(lengthService.getAllLengths());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<LengthDto>getLength(@PathVariable("id") Integer id){
        return ResponseEntity.ok(lengthService.getLengthById(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<LengthDto>createLength(@RequestBody LengthDto lengthDto){
        return ResponseEntity.ok(lengthService.createLength(lengthDto));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<LengthDto>updateCatalog(@PathVariable("id")Integer id, @RequestBody LengthDto lengthDto){
        return ResponseEntity.ok(lengthService.updateLength(lengthDto, id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLength(@PathVariable("id")Integer id){
        lengthService.deleteLength(id);
        return new ResponseEntity(new ApiResponse("Length deleted Successfully",true), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/addLengths")
    public ResponseEntity<?>  addLengths(@RequestBody List<Length> lengths) {

        // TODO: Xử lý logic để thêm nhiều catalog vào trong cơ sở dữ liệu
        lengthService.addLengthsService(lengths);
        // Trả về thông báo thành công nếu thêm thành công
        return new ResponseEntity(new ApiResponse("Add all success!!!", true), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteLengths")
    public ResponseEntity<?> deleteLengths(@RequestBody List<Integer> ids) {
        lengthService.deleteLengthsService(ids);
        return new ResponseEntity(new ApiResponse("Delete all success!!!", true), HttpStatus.OK);
    }
}
