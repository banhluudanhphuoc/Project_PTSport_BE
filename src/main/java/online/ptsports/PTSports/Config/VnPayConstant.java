package online.ptsports.PTSports.Config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class VnPayConstant {
    public static String vnp_Version = "2.1.0";
    public static String vnp_Command = "pay";
    public static String vnp_TmnCode = "0LARC00Y";
    public static String vnp_HashSecret = "HAMLOHFBHIHPNQLRWBWCJCIBUTIPXBQR";
//    public static String vnp_CreateDate;
//    static {
//        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("GMT+7"));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        vnp_CreateDate = currentDateTime.format(formatter);
//    }

    public static String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    //    public static String vnp_BankCode = "NCB";
    public static String vnp_CurrCode = "VND";
    //    public static String vnp_IpAddr = "0:0:0:0:0:0:0:1";
    public static String vnp_Locale = "vn";
    public static String vnp_ReturnUrl = "https://ptsports.online/confirmation";
    //    public static String vnp_ReturnUrl = "http://localhost:3000/confirmation";

}