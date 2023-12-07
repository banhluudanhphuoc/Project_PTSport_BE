package online.ptsports.PTSports.Controller.AdminApi;


import com.cloudinary.Cloudinary;

import online.ptsports.PTSports.Config.AppConstants;
import online.ptsports.PTSports.DTO.PageDto;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.DTO.Response.ResponseDTO;
import online.ptsports.PTSports.DTO.UserDto;
import online.ptsports.PTSports.Service.FileUploadCloudinary;
import online.ptsports.PTSports.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "https://ptsports.online")
public class UserAdminController {
    @Autowired
    private UserService userService;

//    @Value("${user.image.path}")
//    private  String UPLOAD_FOLDER;
    @Autowired
    Cloudinary cloudinary;

    @Autowired
    private FileUploadCloudinary fileUploadCloudinary ;

    private static final Logger logger = LoggerFactory.getLogger(UserAdminController.class);

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //GET user get all
    @GetMapping("/users")
    public ResponseEntity<PageDto<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber",required = false) Integer pageNumber,
            //pageNumber bắt đầu từ 0 trang 0 lấy bao nhiêu phần tử PageSize
            @RequestParam(value="pageSize",required = false) Integer pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_USERS_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir //tăng ha
    ) {
        PageDto<UserDto> allPageUsers= this.userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);


        return new ResponseEntity<PageDto<UserDto>>(allPageUsers, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/users")

    public ResponseDTO< UserDto> add(@Valid @ModelAttribute UserDto u) throws IllegalStateException, IOException {

        String imageURL = fileUploadCloudinary.uploadFile(u.getFile());

        u.setAvatar(imageURL);

        userService.createUser(u);
        return ResponseDTO.<UserDto>builder().status(200).data(u).build();


    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUserBy(@PathVariable("userId") Integer uId) {
        return ResponseEntity.ok(this.userService.getUserById(uId));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uId) throws IOException {

        UserDto userDto = this.userService.getUserById(uId);
        String pathImage =  userDto.getAvatar();
        if(!pathImage.equals("default.png")){
            this.fileUploadCloudinary.deleteImage(pathImage);
        }



        this.userService.deleteUser(uId);
        return new ResponseEntity(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
    }



    //POST -create user
//    @PostMapping("/")
//    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
//           UserDto createUserDto = this.userService.createUser(userDto);
//           return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
//    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //PUT -update user
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable("userId") Integer uId) {
        UserDto updatedUser = this.userService.updateUser(userDto,uId);
        return ResponseEntity.ok(updatedUser);
    }
    //DELETE delete user
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uId) {
//        this.userService.deleteUser(uId);
//        return new ResponseEntity(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
//    }
//
//    //GET user get all
//    @GetMapping("/")
//    public ResponseEntity<PageDto<UserDto>> getAllUsers(
//            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
//            //pageNumber bắt đầu từ 0 trang 0 lấy bao nhiêu phần tử PageSize
//            @RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
//            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_USERS_BY,required = false) String sortBy,
//            @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir //tăng ha
//    ) {
//        PageDto<UserDto> allPageUsers= this.userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
//        return new ResponseEntity<PageDto<UserDto>>(allPageUsers, HttpStatus.OK);
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserDto> getAllUsers(@PathVariable("userId") Integer uId) {
//        return ResponseEntity.ok(this.userService.getUserById(uId));
//    }

//    @PostMapping("/users/avatar/upload/{userId}")
//    public ResponseEntity<UserDto> uploadProductImage(
//            @RequestParam("image") MultipartFile image,
//            @PathVariable Integer userId
//    ) throws IOException {
//        UserDto userDto= this.userService.getUserById(userId);
//
//
//        String fileName =  this.fileService.uploadImage(path,image);
//        userDto.setAvatar(fileName);
//        UserDto updateUser = this.userService.updateUser(userDto,userId);
//        return new ResponseEntity<UserDto>(updateUser,HttpStatus.OK);
//
//    }

    //search
//    @GetMapping("/search/{keywords}")
//    public ResponseEntity<List<UserDto>> searchUserByEmail(
//            @PathVariable("keywords") String keywords
//    ) {
//        List<UserDto> result = this.userService.searchUsers(keywords);
//        return new ResponseEntity<List<UserDto>>(result,HttpStatus.OK);
//
//    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
@GetMapping("/users/count")
    public ResponseEntity<Integer>count(){
        return ResponseEntity.ok(userService.count());
}



}
