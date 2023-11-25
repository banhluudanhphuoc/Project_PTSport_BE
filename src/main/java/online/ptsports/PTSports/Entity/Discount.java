package online.ptsports.PTSports.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discount")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount extends TimeAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double percentage;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
//    @JoinColumn(name = "product_id")
//    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id") // tên field khoá ngoại
    @JsonIgnore
    private Product product;


}
