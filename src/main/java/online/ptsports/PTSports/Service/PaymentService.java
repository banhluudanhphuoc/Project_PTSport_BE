package online.ptsports.PTSports.Service;




import online.ptsports.PTSports.DTO.OrderDto;
import online.ptsports.PTSports.DTO.PaymentDto;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface PaymentService {
    String payWithVNPAY(OrderDto orderDto, HttpServletRequest request) throws UnsupportedEncodingException;

    String payWithMoney(PaymentDto paymentDto, HttpServletRequest request);
}
