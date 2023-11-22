package online.ptsports.PTSports.Controller.PublicApi;

import online.ptsports.PTSports.DTO.BlogDto;
import online.ptsports.PTSports.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/blogs")
@CrossOrigin
public class BlogPublicController {

    @Autowired
    BlogService blogService;


    @GetMapping()
    public ResponseEntity<List<BlogDto>> getAll(){
        return ResponseEntity.ok(blogService.getAllBlogs());
    }


    @GetMapping("/{id}")
    public ResponseEntity<BlogDto>getBlog(@PathVariable("id") Integer id){
        return ResponseEntity.ok(blogService.getBlogById(id));
    }}