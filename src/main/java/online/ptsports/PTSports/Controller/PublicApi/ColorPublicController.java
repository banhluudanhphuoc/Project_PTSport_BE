package online.ptsports.PTSports.Controller.PublicApi;

import online.ptsports.PTSports.DTO.ColorDto;
import online.ptsports.PTSports.Service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/colors")
@CrossOrigin(origins = "https://ptsports.online")
public class ColorPublicController {
    @Autowired
    ColorService colorService;



    @GetMapping()
    public ResponseEntity<List<ColorDto>> getAllColor(){
        return ResponseEntity.ok(colorService.getAllColor());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ColorDto> getOne(@PathVariable("id")Integer id){
        return ResponseEntity.ok(colorService.getColorById(id));
    }}
