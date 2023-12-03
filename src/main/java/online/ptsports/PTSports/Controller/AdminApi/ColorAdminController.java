package online.ptsports.PTSports.Controller.AdminApi;



import online.ptsports.PTSports.DTO.ColorDto;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.Entity.Color;
import online.ptsports.PTSports.Service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/colors")
@CrossOrigin(origins = "https://ptsports.online")
public class ColorAdminController {
    @Autowired
    ColorService colorService;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<ColorDto>>getAllColor(){
        return ResponseEntity.ok(colorService.getAllColor());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ColorDto> getOne(@PathVariable("id")Integer id){
        return ResponseEntity.ok(colorService.getColorById(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<ColorDto>createColor(@RequestBody ColorDto colorDto){
        return ResponseEntity.ok(colorService.createColor(colorDto));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ColorDto>updateColor(@PathVariable("id")Integer id, @RequestBody ColorDto colorDto){
        return ResponseEntity.ok(colorService.updateColor(colorDto, id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteColor(@PathVariable("id")Integer id){
        colorService.deleteColor(id);
        return new ResponseEntity(new ApiResponse("Delete success!!!", true), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/addColors")
    public ResponseEntity<?>  addColors(@RequestBody List<Color> colors) {

        // TODO: Xử lý logic để thêm nhiều màu sắc vào trong cơ sở dữ liệu
           colorService.addColorsService(colors);
        // Trả về thông báo thành công nếu thêm thành công
        return new ResponseEntity(new ApiResponse("Add all success!!!", true), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteColors")
    public ResponseEntity<?> deleteColors(@RequestBody List<Integer> colorIds) {
        colorService.deleteColorsService(colorIds);
        return new ResponseEntity(new ApiResponse("Delete all success!!!", true), HttpStatus.OK);
    }
}
