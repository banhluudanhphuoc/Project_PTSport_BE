package online.ptsports.PTSports.Controller.AdminApi;


import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.Repository.OrderRepo;
import online.ptsports.PTSports.Repository.OrderStatusRepo;
import online.ptsports.PTSports.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/orders")
@CrossOrigin
public class OrderStatusAdminController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/update-order-status")
    public ResponseEntity<?> updateOrderStatus(@RequestParam Integer orderId, @RequestParam Integer newOrderStatusId) {
        try {
            orderService.updateOrderStatus(orderId, newOrderStatusId);
            return new ResponseEntity<>(new ApiResponse("success", true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("error", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
