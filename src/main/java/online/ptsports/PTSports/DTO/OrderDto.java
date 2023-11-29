package online.ptsports.PTSports.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.ptsports.PTSports.Entity.OrderStatus;
import online.ptsports.PTSports.Entity.PaymentMethod;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable{
    private Integer id;
    private String code;
    private Double totalPrice;
    private String customerPhone;
    private String customerName;
    private String customerAddress;
    private String customerEmail;
    private int userID;
    private int orderStatusID;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private int paymentMethodID;
    private List<OrderProductDto>orderProducts;

    public Long vnp_Ammount;
    public String vnp_OrderInfo;
    public String vnp_OrderType = "200000";
    public Long vnp_TxnRef;
//    private UserDto user;
    public Integer type;
    private Boolean status;

}
