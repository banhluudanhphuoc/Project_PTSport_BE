package online.ptsports.PTSports.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ColorDto implements Serializable {
    private  Integer id;
    private String name;
}
