package online.ptsports.PTSports.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;



@NoArgsConstructor
@Data
public class RoleDto {
    @Id
    private Integer roleId;


    private String roleName;
}
