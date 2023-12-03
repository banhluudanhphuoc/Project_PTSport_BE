//package online.ptsports.PTSports.Security.JWT;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.nimbusds.jose.Algorithm;
//import edu.kits.movie.Dto.Response.DecodeJWTResponse;
//import lombok.RequiredArgsConstructor;
//import online.ptsports.PTSports.DTO.Response.DecodeJWTResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//import static java.util.Arrays.stream;
//
//@Component
//@RequiredArgsConstructor
//public class JwtUtils {
//
//    @Value("${spring.app.jwtSecret}")
//    private String jwtSecret;
//
//
//    public String createRefreshToken(String name) {
//        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//        return JWT.create().withSubject(name)
//                .withExpiresAt(Instant.now().plusSeconds(24 * 60 * 60 * 30))
//                .sign(algorithm);
//    }
//
//    public DecodeJWTResponse decodeJWT(String token) throws Exception {
//        DecodeJWTResponse jwt = new DecodeJWTResponse();
//        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//        JWTVerifier verifier = JWT.require(algorithm).build();
//        DecodedJWT decodedJWT = verifier.verify(token);
//        String username = decodedJWT.getSubject();
//        jwt.setName(username);
//        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
//        if (roles != null) {
//            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
//            jwt.setAuthority(authorities);
//        }
//        return jwt;
//    }
//
//    public String createToken(Authentication authentication) {
//        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//        return JWT.create().withSubject(authentication.getName())
//                .withExpiresAt(Instant.now().plusSeconds(60 * 60))
//                .withClaim("roles", authentication.getAuthorities()
//                        .stream()
//                        .map(GrantedAuthority::getAuthority)
//                        .collect(Collectors.toList()))
//                .sign(algorithm);
//    }
//}
