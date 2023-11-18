package online.ptsports.PTSports.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtAuthRequest {

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String password;

}
