//package com.ptsports.myproject.Controller;
//
//
//import com.ptsports.myproject.DTO.Request.JwtAuthRequest;
//import com.ptsports.myproject.DTO.Response.JwtAuthResponse;
//import com.ptsports.myproject.DTO.ResponseFetchMe;
//import com.ptsports.myproject.DTO.UserDto;
//import com.ptsports.myproject.Entity.User;
//import com.ptsports.myproject.Repository.UserRepo;
//import com.ptsports.myproject.Security.JWT.JwtTokenService;
//import com.ptsports.myproject.Service.UserService;
//import com.ptsports.myproject.Utils.CookieUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.security.Principal;
//
////su dung http://localhost:8080/swagger-ui/index.html  de xem api doc
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin
//public class AuthController {
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    JwtTokenService jwtTokenService;
//
//    @Autowired
//    UserService userService;
//
//   @Autowired
//    UserDetailsService userDetailsService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepo userRepo;
//
//    @GetMapping("/me")
//    @PreAuthorize("isAuthenticated()")
//
//    public ResponseEntity<ResponseFetchMe> me(Principal p) {
//        String username = p.getName();
//        UserDto userDto = userService.findByEmail(username);
//
//        ResponseFetchMe res = new ResponseFetchMe(userDto.getUserId(),userDto.getName()
//                ,userDto.getEmail(),userDto.getAvatar(),userDto.getRoles().get(0).getRoleName());
////user login success
//
//        return new ResponseEntity<ResponseFetchMe>(res, HttpStatus.OK);
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<JwtAuthResponse> registerHandler(@Valid @RequestBody UserDto user) {
//        String encodedPass = passwordEncoder.encode(user.getPassword());
//
//        user.setPassword(encodedPass);
//
//        UserDto userDto = userService.registerUser(user);
//
//        String token = jwtTokenService.createToken(userDto.getEmail());
//
//
//        return ResponseEntity.ok(new JwtAuthResponse(token,true));
//    }
//
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody @Valid JwtAuthRequest jwtAuthRequest) {
//        try {
//            // Authenticate user
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(jwtAuthRequest.getEmail(), jwtAuthRequest.getPassword())
//            );
//        } catch (BadCredentialsException e) {
//            // Handle authentication failure
//            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
//        }
//
//        // Load user details
//        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());
//
//        // Check if the email is verified
//        User user = userRepo.findByEmail(jwtAuthRequest.getEmail());
//        if (user != null && !user.getIsActive()) {
//            return new ResponseEntity<>("Email is not verified", HttpStatus.UNAUTHORIZED);
//        }
//
//        // Generate JWT token
//        String token = jwtTokenService.createToken(userDetails);
//
//        // Send the token in the response
//        return ResponseEntity.ok(new JwtAuthResponse(token, true));
//    }
//
//
//
//    @GetMapping("/logout")
//    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
//
//        // Cách 1: Xoá token khỏi client bằng cookie
//        CookieUtil.clearTokenCookie(request);
//
//
//
//        // Cách 2: Xoá token khỏi client bằng giải phóng token khỏi local storage hoặc session storage
//        // localStorage.removeItem('jwtToken');
//        // sessionStorage.removeItem('jwtToken');
//
//        return ResponseEntity.ok("Logged out successfully");
//    }
//
//    @GetMapping("/verify-email")
//    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
//        // Tìm user dựa trên token
//        User user = userRepo.findByEmailVerificationToken(token);
//        if (user == null) {
//            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
//        }
//
//        // Xác thực email và lưu vào cơ sở dữ liệu
//        user.setIsActive(true);
//        user.setEmailVerificationToken(null);
//        userRepo.save(user);
//
//        return new ResponseEntity<>("Email verified successfully", HttpStatus.OK);
//    }
//
//
//
//
//}


package online.ptsports.PTSports.Controller;



import online.ptsports.PTSports.DTO.Request.JwtAuthRequest;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.DTO.Response.JwtAuthResponse;
import online.ptsports.PTSports.DTO.ResponseFetchMe;
import online.ptsports.PTSports.DTO.UserDto;
import online.ptsports.PTSports.Entity.User;
import online.ptsports.PTSports.Repository.UserRepo;
import online.ptsports.PTSports.Security.JWT.JwtTokenService;
import online.ptsports.PTSports.Service.UserService;
import online.ptsports.PTSports.Utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseFetchMe> me(Principal p) {
        String username = p.getName();
        UserDto userDto = userService.findByEmail(username);

        ResponseFetchMe res = new ResponseFetchMe(userDto.getUserId(), userDto.getName(), userDto.getEmail(),
                userDto.getAvatar(), userDto.getRoles().get(0).getRoleName());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerHandler(@Valid @RequestBody UserDto user) {
        String encodedPass = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPass);

        UserDto userDto = userService.registerUser(user);

        String token = jwtTokenService.createToken(userDto.getEmail());

        return ResponseEntity.ok(new ApiResponse("Đăng ký thành công , vui lòng kiểm tra email", true));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid JwtAuthRequest jwtAuthRequest) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtAuthRequest.getEmail(), jwtAuthRequest.getPassword()));
        } catch (BadCredentialsException e) {
            // Handle authentication failure
            return new ResponseEntity<>("Email hoặc mật khẩu không hợp lệ", HttpStatus.UNAUTHORIZED);
        }

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());

// Check if the email is verified
        User user = userRepo.findByEmail(jwtAuthRequest.getEmail());
        if (user != null && !user.getIsActive()) {
            return new ResponseEntity<>("Email chưa được xác minh", HttpStatus.UNAUTHORIZED);
        }

// Generate JWT token
        String token = jwtTokenService.createToken(userDetails.getUsername());

// Send the token in the response
        return ResponseEntity.ok(new JwtAuthResponse(token, true));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        // Cách 1: Xoá token khỏi client bằng cookie
        CookieUtil.clearTokenCookie(request);

        // Cách 2: Xoá token khỏi client bằng giải phóng token khỏi local storage hoặc session storage
        // localStorage.removeItem('jwtToken');
        // sessionStorage.removeItem('jwtToken');

        return ResponseEntity.ok("Đăng xuất thành công");
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        // Tìm user dựa trên token
        User user = userRepo.findByEmailVerificationToken(token);
        if (user == null) {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }

        // Xác thực email và lưu vào cơ sở dữ liệu
        user.setIsActive(true);
        user.setEmailVerificationToken(null);
        userRepo.save(user);

        return new ResponseEntity<>("Email đã được xác minh thành công", HttpStatus.OK);
    }
}

