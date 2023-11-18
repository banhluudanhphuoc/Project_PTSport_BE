package online.ptsports.PTSports.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "social_login")
public class SocialLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_login_id", nullable = false)
    private Integer id;

    @Column(name = "provider", length = 50)
    private String provider;

    @OneToMany(mappedBy = "socialLogin")
    private List<User> users;

}
