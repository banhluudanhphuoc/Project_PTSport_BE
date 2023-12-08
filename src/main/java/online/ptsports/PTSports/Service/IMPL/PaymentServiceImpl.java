package online.ptsports.PTSports.Service.IMPL;



import online.ptsports.PTSports.Config.Config;
import online.ptsports.PTSports.Config.VnPayConstant;
import online.ptsports.PTSports.DTO.OrderDto;
import online.ptsports.PTSports.DTO.PaymentDto;
import online.ptsports.PTSports.Service.PaymentService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public String payWithVNPAY(OrderDto orderDto, HttpServletRequest request) throws UnsupportedEncodingException {
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String vnp_CreateDate = formatter.format(cld.getTime());
//
//        cld.add(Calendar.MINUTE,15);
//        String vnp_ExpireDate = formatter.format(cld.getTime());

        LocalDateTime now = LocalDateTime.now(); // Lấy thời gian hiện tại
        ZoneOffset zoneOffset = ZoneOffset.ofHours(7); // Đặt múi giờ +7

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

// Thêm 7 tiếng vào vnp_CreateDate
        LocalDateTime vnp_CreateDateTime = now.plusHours(7);
        String vnp_CreateDate = vnp_CreateDateTime.format(formatter);

// Thêm 7 tiếng 15 phút vào vnp_ExpireDate
        LocalDateTime expireDateTime = now.plusMinutes(15).plusHours(7);
        String vnp_ExpireDate = expireDateTime.format(formatter);

        Map<String,String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnPayConstant.vnp_Version);
        vnp_Params.put("vnp_Command",VnPayConstant.vnp_Command);
        vnp_Params.put("vnp_TmnCode",VnPayConstant.vnp_TmnCode);
        vnp_Params.put("vnp_Amount",String.valueOf(orderDto.vnp_Ammount));
//        vnp_Params.put("vnp_BankCode", VnPayConstant.vnp_BankCode);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_CurrCode",VnPayConstant.vnp_CurrCode);
        vnp_Params.put("vnp_IpAddr", Config.getIpAddress(request));
        vnp_Params.put("vnp_Locale",VnPayConstant.vnp_Locale);
        vnp_Params.put("vnp_OrderInfo",orderDto.vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType",orderDto.vnp_OrderType);
        vnp_Params.put("vnp_ReturnUrl", VnPayConstant.vnp_ReturnUrl);
        vnp_Params.put("vnp_TxnRef", String.valueOf(orderDto.vnp_TxnRef));
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldList = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldList);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator itr =  fieldList.iterator();
        while (itr.hasNext()){
            String fieldName = (String) itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if(fieldValue!=null && (fieldValue.length()>0)){
                hashData.append(fieldName);
                hashData.append("=");
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append("=");
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if(itr.hasNext()){
                    query.append("&");
                    hashData.append("&");
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(VnPayConstant.vnp_HashSecret,hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPayConstant.vnp_Url + "?" + queryUrl;
        return paymentUrl;
    }

    @Override
    public String payWithMoney(PaymentDto paymentDto, HttpServletRequest request) {
        return null;
    }
}
