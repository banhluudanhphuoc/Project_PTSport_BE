package online.ptsports.PTSports.Service;


import online.ptsports.PTSports.DTO.DiscountDto;
import online.ptsports.PTSports.Entity.Discount;

public interface DiscountService {

        Discount addDiscount(DiscountDto discountDTO);
        void deleteDiscountById(Integer discountId);

        Discount getDiscountById(Integer discountId);
}
