package online.ptsports.PTSports.Service;

import online.ptsports.PTSports.DTO.DiscountDTO;

import java.util.List;

public interface DiscountService {
    List<DiscountDTO> getDiscountsByProductId(Integer productId);
    DiscountDTO addDiscount(DiscountDTO discountDTO);


    DiscountDTO updateDiscount(DiscountDTO discountDTO, Integer Id);

    void deleteDiscount(Integer discountId);
}
