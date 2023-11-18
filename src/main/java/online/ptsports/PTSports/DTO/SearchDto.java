package online.ptsports.PTSports.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
//    private String name;
    private Integer color;
    private Integer size;
    private Integer catalogID;
    private Integer lengthID;
    private double startPrice;
    private Double endPrice;
}
