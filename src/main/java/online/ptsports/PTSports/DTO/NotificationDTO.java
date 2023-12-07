package online.ptsports.PTSports.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDTO {

    private Integer notificationId;
    private String description;
    private Integer userId;
    private Integer orderId;
}
