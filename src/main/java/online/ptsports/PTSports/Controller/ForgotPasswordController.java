package online.ptsports.PTSports.Controller;

import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.DTO.UserDto;
import online.ptsports.PTSports.Entity.PasswordResetToken;


import online.ptsports.PTSports.Service.EmailService;
import online.ptsports.PTSports.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth/forgot-password")
@CrossOrigin(origins = "https://ptsports.online")
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/request")
    public ResponseEntity<?> requestResetPassword(@RequestParam("email") String email) {
        try {
        UserDto userDto = userService.findByEmail(email);

        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Người dùng không tồn tại", false));
        }

        PasswordResetToken passwordResetToken = userService.createPasswordResetToken(userDto);

        // Gửi email chứa liên kết đặt lại mật khẩu
        sendResetPasswordEmail(userDto.getEmail(), passwordResetToken.getToken());

            return new ResponseEntity<>(new ApiResponse("Liên kết thay đổi mật khẩu đã gửi về email của bạn", true), HttpStatus.OK);
    } catch (Exception e){
            return new ResponseEntity<>(new ApiResponse("Người dùng không tồn tại", false),HttpStatus.UNAUTHORIZED);
        }}

    @PostMapping("/reset")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam("token") String token,
                                                     @RequestParam("password") String newPassword) {
        PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);

        if (passwordResetToken == null || passwordResetToken.getExpiryDate().before(new Date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Token không hợp lệ hoặc đã hết hạn", false));
        }

        UserDto userDto = new UserDto();
        userDto.setPassword(newPassword);  // Đặt mật khẩu mới vào DTO
        userService.updatePasswords(userDto, token);

        // Xóa token sau khi đã sử dụng
        userService.deletePasswordResetToken(passwordResetToken);

        return ResponseEntity.ok(new ApiResponse("Mật khẩu đã được đặt lại thành công", true));
    }


    private void sendResetPasswordEmail(String email, String token) {
        String resetLink = "https://ptsports.online/reset-password?token=" + token;

        String emailContent = "Để đặt lại mật khẩu, hãy nhấp vào liên kết sau:\n" + resetLink;

        // Gửi email sử dụng EmailService
        emailService.sendEmail(email, "Yêu cầu Đặt Lại Mật Khẩu", emailContent);
    }
}

