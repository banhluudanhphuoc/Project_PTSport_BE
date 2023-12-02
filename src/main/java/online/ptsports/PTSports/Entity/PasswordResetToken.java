//package online.ptsports.PTSports.Entity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Calendar;
//import java.util.Date;
//
//@Data
//@Entity
//public class PasswordResetToken {
//
//    private static final int EXPIRATION = 60 * 24; // 24 giờ
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String token;
//
//    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "user_id")
//    private User user;
//
//    private Date expiryDate;
//
//    public PasswordResetToken() {
//        this.expiryDate = calculateExpiryDate(EXPIRATION);
//    }
//
//    public PasswordResetToken(String token, User user) {
//        this.token = token;
//        this.user = user;
//        this.expiryDate = calculateExpiryDate(EXPIRATION);
//    }
//
//    private Date calculateExpiryDate(int expiryTimeInMinutes) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
//        return new Date(cal.getTime().getTime());
//    }
//}
