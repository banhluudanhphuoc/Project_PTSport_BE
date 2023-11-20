package online.ptsports.PTSports.Controller.AdminApi;



import online.ptsports.PTSports.DTO.BlogDto;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin/blogs")
@CrossOrigin
public class BlogController {

    @Autowired
    BlogService blogService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<BlogDto>> getAll(){
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BlogDto>getBlog(@PathVariable("id") Integer id){
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<BlogDto>createBlog(@RequestBody BlogDto catalogDto){
        return ResponseEntity.ok(blogService.createBlog(catalogDto));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<BlogDto>updateBlog(@PathVariable("id")Integer id, @RequestBody BlogDto catalogDto){
        return ResponseEntity.ok(blogService.updateBlog(catalogDto, id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable("id")Integer id){
        blogService.deleteBlog(id);
        return new ResponseEntity(new ApiResponse("User deleted Successfully",true), HttpStatus.OK);
    }

}