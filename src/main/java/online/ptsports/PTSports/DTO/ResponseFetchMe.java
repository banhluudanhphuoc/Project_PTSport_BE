package online.ptsports.PTSports.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFetchMe {
    private Integer userId;


    private String name;


    private String email;

    private String avatar;



    private String role;
}
