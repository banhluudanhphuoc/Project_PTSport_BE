package online.ptsports.PTSports.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends TimeAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String detail;
    private double price;

    private int totalQuantity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY/* tên property product trong class ProductImages */
            /*, fetch = FetchType.EAGER, orphanRemoval = true*/)
    private List<ImageProduct> listImage;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name="catalog_id")
    private Catalog catalog;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name="length_id")
    private Length length;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "product_size", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<Size> sizes;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "product_color", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "color_id"))
    private Set<Color> colors;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name="category_id")
    private Category category;


    @Column(name = "discounted_price")
    private Double discountedPrice;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;




//    @ManyToMany(mappedBy = "product", cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<WishList> wishLists;

    public void addProductImages(ImageProduct productImages1) {
        productImages1.setProduct(this);
        listImage.add(productImages1);
    }

    public void clearProductImages() {
        for (ImageProduct productImages : listImage) {
            productImages.setProduct(null);
        }
        listImage.clear();
    }

    public void applyDiscount(Discount discount) {
        if (discount != null) {
            double discountPercentage = discount.getPercentage();
            this.discount = discount;
            this.discountedPrice = this.price - (this.price * (discountPercentage / 100.0));
        } else {
            this.discount = null;
            this.discountedPrice = this.price;
        }
    }


}