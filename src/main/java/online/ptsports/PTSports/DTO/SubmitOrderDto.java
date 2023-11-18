package online.ptsports.PTSports.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubmitOrderDto {

    private  String string;

    private OrderDto orderDto;
}
