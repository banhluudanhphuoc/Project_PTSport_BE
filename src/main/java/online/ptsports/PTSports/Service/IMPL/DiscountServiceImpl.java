package online.ptsports.PTSports.Service.IMPL;

import online.ptsports.PTSports.DTO.DiscountDTO;
import online.ptsports.PTSports.Entity.Discount;
import online.ptsports.PTSports.Exeption.ResoureNotFoundException;
import online.ptsports.PTSports.Repository.DiscountRepo;
import online.ptsports.PTSports.Service.DiscountService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepo discountRepo;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DiscountDTO> getDiscountsByProductId(Integer productId) {
        List<Discount> discounts = discountRepo.findByProductId(productId);
        return discounts.stream()
                .map(discount -> modelMapper.map(discount, DiscountDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public DiscountDTO addDiscount(DiscountDTO discountDTO) {
        Discount discount = convertToDiscount(discountDTO);
        discountRepo.save(discount);
        return convertToDiscountDto(discount);
    }



//    @Override
//    public DiscountDTO updateDiscount(Integer discountId, DiscountDTO discountDTO) {
//        Discount existingDiscount = discountRepo.findById(discountId)
//                .orElseThrow(() -> new ResourceNotFoundException("Discount", "id", discountId));
//
//        modelMapper.map(discountDTO, existingDiscount);
//        discountRepo.save(existingDiscount);
//        return modelMapper.map(existingDiscount, DiscountDTO.class);
//    }

    @Override
    public DiscountDTO updateDiscount(DiscountDTO discountDTO, Integer Id) {
        Discount discount = discountRepo.findById(Id).orElseThrow(()
                -> new ResoureNotFoundException("Discount", "ID", Id));
        Discount discountNew  = this.convertToDiscount(discountDTO);
        discountNew.setId(discount.getId());
        discountRepo.save(discount);
        return this.convertToDiscountDto(discountNew);
    }

//    @Override
//    public void deleteDiscount(Integer discountId) {
//        Discount discount = discountRepo.findById(discountId)
//                .orElseThrow(() -> new ResourceNotFoundException("Discount", "id", discountId));
//
//        discountRepo.delete(discount);
//    }


    @Override
    public void deleteDiscount(Integer discountId) {
        Discount discount = discountRepo.findById(discountId).orElseThrow(()-> new ResoureNotFoundException("Discount", "ID", discountId));
        discountRepo.deleteById(discount.getId());
    }

    public DiscountDTO convertToDiscountDto(Discount discount){
        return this.modelMapper.map(discount, DiscountDTO.class);

    }
    public Discount convertToDiscount(DiscountDTO discountDto){
        return this.modelMapper.map(discountDto, Discount.class);

    }
}
