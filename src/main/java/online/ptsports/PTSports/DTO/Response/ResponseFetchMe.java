package online.ptsports.PTSports.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFetchMe {
    private Integer userId;


    private String name;


    private String email;

    private String avatar;

    private Date birthdate;


    private String role;
}
