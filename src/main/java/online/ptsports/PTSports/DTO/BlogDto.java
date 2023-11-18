package online.ptsports.PTSports.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto {
    private Integer blogId;
    private String title;
    private String content;
    private int categoryID;
    private CategoryDto category;
}
