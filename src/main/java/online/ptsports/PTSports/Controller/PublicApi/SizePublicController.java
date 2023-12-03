package online.ptsports.PTSports.Controller.PublicApi;

import online.ptsports.PTSports.DTO.SizeDto;
import online.ptsports.PTSports.Service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/sizes")
@CrossOrigin(origins = "https://ptsports.online")
public class SizePublicController {
    @Autowired
    SizeService sizeService;


    @GetMapping()
    public ResponseEntity<List<SizeDto>> getAllSize(){
        return ResponseEntity.ok(sizeService.getAllSize());
    }


    @GetMapping("/{id}")
    public ResponseEntity<SizeDto> getOne(@PathVariable("id")Integer id){
        return ResponseEntity.ok(sizeService.getSizeById(id));
    }}
