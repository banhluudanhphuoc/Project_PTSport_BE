package online.ptsports.PTSports.Controller.AdminApi;


import online.ptsports.PTSports.DTO.Request.JwtAuthRequest;
import online.ptsports.PTSports.DTO.Response.JwtAuthResponse;
import online.ptsports.PTSports.Entity.User;
import online.ptsports.PTSports.Repository.UserRepo;
import online.ptsports.PTSports.Security.JWT.JwtTokenService;
import online.ptsports.PTSports.Service.UserService;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AuthAdminController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;



    @Autowired
    private UserRepo userRepo;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody @Valid JwtAuthRequest jwtAuthRequest) {
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
}
