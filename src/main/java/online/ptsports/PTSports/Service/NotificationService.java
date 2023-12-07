package online.ptsports.PTSports.Service;

import online.ptsports.PTSports.DTO.NotificationDTO;
import online.ptsports.PTSports.Entity.Notification;
import online.ptsports.PTSports.Entity.Order;
import online.ptsports.PTSports.Entity.User;

import java.util.List;

public interface NotificationService {

    void createNotificationForCompletedOrder(Order order, User user);

    List<NotificationDTO> getNotificationsByUserId(Integer userId);



    NotificationDTO convertToNotificationDto(Notification notification);
    Notification convertToNotification(NotificationDTO notificationDTO);
}
