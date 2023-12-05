package online.ptsports.PTSports.Controller.PublicApi;



import online.ptsports.PTSports.DTO.CartDto;
import online.ptsports.PTSports.DTO.OrderDto;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.DTO.SubmitOrderDto;
import online.ptsports.PTSports.Entity.Order;
import online.ptsports.PTSports.Entity.OrderStatus;
import online.ptsports.PTSports.Exeption.ResoureNotFoundException;
import online.ptsports.PTSports.Repository.OrderRepo;
import online.ptsports.PTSports.Repository.OrderStatusRepo;
import online.ptsports.PTSports.Service.CartService;
import online.ptsports.PTSports.Service.OrderService;
import online.ptsports.PTSports.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/public/payment")
@CrossOrigin(origins = "https://ptsports.online")
public class PaymentPublicController {
    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;
    @Autowired
    private OrderStatusRepo orderStatusRepo;


    @PostMapping("/pay")
    public String pay(@RequestBody OrderDto orderDto, HttpServletRequest request) {
        try {
//luu orderDto vao localStore
            CartDto cart = cartService.getCart(orderDto.getUserID());

            long current = System.currentTimeMillis();

//            Random random = new Random();
//            long randomNumber = random.nextInt(90000) + 10000; // tạo số ngẫu nhiên từ 10000 đến 99999
            orderDto.setVnp_TxnRef(current);
            orderDto.setCode("ORDER-"+current);
            orderDto.setVnp_OrderInfo(orderDto.getCode());

           double price = 0;
            for (int i = 0; i < cart.getItemList().size(); i++) {
                price+= cart.getItemList().get(i).getTotalPrice();
            }

            orderDto.setVnp_Ammount((long)price * 100);
            return paymentService.payWithVNPAY(orderDto, request);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }



//    @PostMapping("/submit-order")
//    public ResponseEntity<?>submit(@RequestBody SubmitOrderDto result, HttpServletRequest request, HttpSession httpSession){
//        OrderDto orderDto = result.getOrderDto();
//        String[] out = result.getString().split("&");
//        for (int i = 0; i < out.length; i++) {
//
//            if(out[i].startsWith("vnp_OrderInfo")){
//                String[]code = out[i].split("=");
//                orderDto.setCode(code[1]);
//            }
//            if(out[i].startsWith("vnp_ResponseCode")){
//                String[]code = out[i].split("=");
//                if(code[1].equals("00")){
//                    System.out.println("--------------code: "+ code[1]);
//                    orderDto.setType(1);
//
//                    orderService.saveOrderService(orderDto, httpSession);
//                }
//            }
//
//        }
//
//        // Gán trạng thái "Đã Xác Nhận : Id = 2"cho đơn hàng
//        Order order = new Order();
//        OrderStatus updateStatus = orderStatusRepo.findById(2)
//                .orElseThrow(() -> new ResoureNotFoundException("OrderStatus", "ID", 2));
//
//        order.setOrderStatus(updateStatus);
//        return new ResponseEntity<>(new ApiResponse("success", true), HttpStatus.OK);
//
//    }

    @PostMapping("/submit-order")
    public ResponseEntity<?> submit(@RequestBody SubmitOrderDto result, HttpServletRequest request, HttpSession httpSession) {
        OrderDto orderDto = result.getOrderDto();
        String[] out = result.getString().split("&");
        for (int i = 0; i < out.length; i++) {
            if (out[i].startsWith("vnp_OrderInfo")) {
                String[] code = out[i].split("=");
                orderDto.setCode(code[1]);
            }
            if (out[i].startsWith("vnp_ResponseCode")) {
                String[] code = out[i].split("=");
                if (code[1].equals("00")) {
                    System.out.println("--------------code: " + code[1]);
                    orderDto.setType(1);

                    // Cập nhật trạng thái của đơn hàng thành "Đã thanh toán"
                    OrderStatus paidStatus = orderStatusRepo.findById(2)
                            .orElseThrow(() -> new ResoureNotFoundException("OrderStatus", "ID", 2));

                    orderDto.setOrderStatus(paidStatus);
                    orderDto.setOrderStatusID(paidStatus.getId());  // Set giá trị cho orderStatusID
                    orderDto.setStatus(true);  // Set giá trị cho status

                    orderService.saveOrderService(orderDto, httpSession);
                }
            }
        }

        return new ResponseEntity<>(new ApiResponse("success", true), HttpStatus.OK);
    }

//    @PostMapping("/submit-order")
//    public ResponseEntity<?> submitOrder(@RequestBody SubmitOrderDto result, HttpServletRequest request, HttpSession httpSession) {
//        OrderDto orderDto = result.getOrderDto();
//        String[] out = result.getString().split("&");
//
//
//
//        for (int i = 0; i < out.length; i++) {
//            if (out[i].startsWith("vnp_OrderInfo")) {
//                String[] code = out[i].split("=");
//                orderDto.setCode(code[1]);
//            }
//            if (out[i].startsWith("vnp_ResponseCode")) {
//                String[] code = out[i].split("=");
//                if (code[1].equals("00")) {
//                    System.out.println("--------------code: " + code[1]);
//                    orderDto.setType(1);
//
//                    // Cập nhật trạng thái của đơn hàng thành "Đã Xác nhận"
//                    OrderStatus paidStatus = orderStatusRepo.findById(2)
//                            .orElseThrow(() -> new ResoureNotFoundException("OrderStatus", "ID", 2));
//
//                    orderDto.setOrderStatus(paidStatus);
//                    orderDto.setOrderStatusID(paidStatus.getId());  // Set giá trị cho orderStatusID
//                    orderDto.setStatus(true);  // Set giá trị cho status
//                }
//            }
//        }
//
//        // Xử lý tạo đơn hàng
//        createOrder(orderDto, httpSession);
//
//        return new ResponseEntity<>(new ApiResponse("success", true), HttpStatus.OK);
//    }

    @PostMapping("/money")
    // Phương thức tạo đơn hàng
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto, HttpSession httpSession){

        // Kiểm tra paymentMethodID và xử lý tạo đơn hàng
        if (orderDto.getPaymentMethodID() == 1) {
            // Tạo đơn hàng với status = true và orderStatusID = 1
            orderDto.setType(1);
            orderDto.setStatus(true);
            orderDto.setOrderStatusID(1);
        }

        long current = System.currentTimeMillis();
        orderDto.setCode("ORDER-"+current);
        orderDto.setVnp_OrderInfo("thanh toan hoa don ORDER-"+current);

        // Gọi phương thức saveOrderService để lưu đơn hàng
        orderService.saveOrderService(orderDto, httpSession);
        return new ResponseEntity(new ApiResponse("success", true), HttpStatus.OK);
    }


}
