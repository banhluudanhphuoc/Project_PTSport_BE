package online.ptsports.PTSports.Service.IMPL;

import online.ptsports.PTSports.DTO.NotificationDTO;
import online.ptsports.PTSports.Entity.Notification;
import online.ptsports.PTSports.Entity.Order;
import online.ptsports.PTSports.Entity.User;

import online.ptsports.PTSports.Repository.NotificationRepo;
import online.ptsports.PTSports.Service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepo notificationRepo;


    @Autowired
    ModelMapper modelMapper;


    @Override
    @Transactional
    public void createNotificationForCompletedOrder(Order order, User user) {
        if (order.getOrderStatus() != null && order.getOrderStatus().getId() == 6) {
            Notification notification = new Notification();
            notification.setDescription("Đơn hàng đã hoàn thành");
            notification.setOrder(order);
            notification.setUser(user);
            notificationRepo.save(notification);
        }
    }

    @Override
    @Transactional
    public List<NotificationDTO> getNotificationsByUserId(Integer userId) {
        List<Notification> notifications = notificationRepo.findByUserId(userId);
        return notifications.stream()
                .map(this::convertToNotificationDto)
                .collect(Collectors.toList());
    }


    @Override
    public NotificationDTO convertToNotificationDto(Notification notification) {
        return modelMapper.map(notification, NotificationDTO.class);
    }

    @Override
    public Notification convertToNotification(NotificationDTO notificationDTO) {
        return modelMapper.map(notificationDTO, Notification.class);
    }
}
