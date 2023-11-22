package online.ptsports.PTSports.Controller.PublicApi;

import com.cloudinary.Cloudinary;

import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.DTO.Response.ResponseDTO;
import online.ptsports.PTSports.DTO.UserDto;
import online.ptsports.PTSports.Service.FileUploadCloudinary;
import online.ptsports.PTSports.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/public")
@CrossOrigin
public class UserPublicController {
    @Autowired
    private UserService userService;


    @Autowired
    Cloudinary cloudinary;

    @Autowired
    private FileUploadCloudinary fileUploadCloudinary ;

    @PostMapping("/avatar/upload/{userId}")
    public ResponseEntity<UserDto> uploadProductImage(@RequestParam("image") MultipartFile image,
                                                      @PathVariable Integer userId) throws IOException {
        UserDto userDto= this.userService.getUserById(userId);


        String imageURL = fileUploadCloudinary.uploadFile(image);
        userDto.setAvatar(imageURL);
        UserDto updateUser = this.userService.updateAvatar(userDto,userId);
        return new ResponseEntity<UserDto>(updateUser, HttpStatus.OK);

    }

    @PutMapping("/user/password")
    public ResponseDTO<Void> updatePassword(
            @RequestBody  UserDto u) {
        userService.updatePassword(u);
        return ResponseDTO.<Void>builder().status(200).build();
    }




    @DeleteMapping("/session/delete")
    public ResponseEntity<?> deleteSession(final HttpServletRequest request, final HttpServletResponse response) {
//        Cart cart = (Cart) request.getSession().getAttribute("cart");
//
        request.getSession().invalidate();
        return new ResponseEntity<>(new ApiResponse("Delete success", true), HttpStatus.OK);
    }
}
