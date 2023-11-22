package online.ptsports.PTSports.Controller.PublicApi;

import online.ptsports.PTSports.DTO.LengthDto;
import online.ptsports.PTSports.Service.LengthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/lengths")
@CrossOrigin
public class LengthPublicController {
    @Autowired
    LengthService lengthService;


    @GetMapping()
    public ResponseEntity<List<LengthDto>> getAll(){
        return ResponseEntity.ok(lengthService.getAllLengths());
    }


    @GetMapping("/{id}")
    public ResponseEntity<LengthDto>getLength(@PathVariable("id") Integer id){
        return ResponseEntity.ok(lengthService.getLengthById(id));
    }}