package online.ptsports.PTSports.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.ptsports.PTSports.Entity.Discount;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private String detail;
    private double price;
    private Integer totalQuantity;
    private int catalogID;
    private int lengthIDX;
    private LengthDto length;
    private  CatalogDto catalog;
    private int categoryID ;
    private CategoryDto category;
    private Set<MultipartFile> files = new HashSet<>();
    List<ImageProductDto> listImage = new ArrayList<>();
    List<Integer>sizesID = new ArrayList<>();
    List<Integer>colorsID = new ArrayList<>();
    List<SizeDto>sizes = new ArrayList<>();
    List<ColorDto>colors = new ArrayList<>();

//    private Discount discount;
    private Double discountedPrice;
    private int discountID;

}

