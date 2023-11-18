package online.ptsports.PTSports.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentDto implements Serializable {
    public Long vnp_Ammount;
    public String vnp_OrderInfo;
    public String vnp_OrderType = "200000";
    public Long vnp_TxnRef;
}