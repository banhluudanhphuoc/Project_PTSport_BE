package online.ptsports.PTSports.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto implements Serializable {
//    static final long serialVersionUID = 2915399222309278762l;
    private Integer id;
    private int productID;
    private String productName;
    private String image;
    private int sizeID;
    private int colorID;
    private SizeDto size;
    private ColorDto color;
    private int quantity;
    private double price;
    private Double totalPrice;


}
