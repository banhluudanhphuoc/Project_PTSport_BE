package online.ptsports.PTSports.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListDto implements Serializable {
    private Integer id;

    private Integer userID;

    private List<ProductDto> productDtos;
}
