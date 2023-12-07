package online.ptsports.PTSports.Controller.PublicApi;


import online.ptsports.PTSports.DTO.NotificationDTO;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.Entity.Notification;
import online.ptsports.PTSports.Entity.User;
import online.ptsports.PTSports.Repository.NotificationRepo;
import online.ptsports.PTSports.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/public/notifications")
@CrossOrigin(origins = "https://ptsports.online")
public class NotificationPublicController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepo notificationRepo;


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Integer userId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deteNotificationById(@RequestParam("id") int id,
                                                            final HttpServletRequest request, final HttpServletResponse response){
        notificationRepo.deleteById(id);
        return new ResponseEntity<>(new ApiResponse("Delete success!!!", true), HttpStatus.OK);
    }







}
