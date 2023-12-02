package online.ptsports.PTSports.Service.IMPL;


import online.ptsports.PTSports.DTO.CategoryDto;
import online.ptsports.PTSports.DTO.DiscountDto;
import online.ptsports.PTSports.Entity.Category;
import online.ptsports.PTSports.Entity.Discount;

import online.ptsports.PTSports.Repository.DiscountRepo;
import online.ptsports.PTSports.Service.DiscountService;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// DiscountServiceImpl.java
@Service
public class DiscountServiceImpl implements DiscountService {

//    private final DiscountRepo discountRepo;

    @Autowired
    DiscountRepo discountRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    public DiscountServiceImpl(DiscountRepo discountRepo) {
        this.discountRepo = discountRepo;
    }

    @Override
    public Discount addDiscount(DiscountDto discountDTO) {
        Discount discount = new Discount();
        discount.setPercentage(discountDTO.getPercentage());
        return discountRepo.save(discount);
    }

    @Override
    public void deleteDiscountById(Integer discountId) {
        discountRepo.deleteById(discountId);
    }


    @Override
    public Discount getDiscountById(Integer discountId) {
        Optional<Discount> optionalDiscount = discountRepo.findById(discountId);
        return optionalDiscount.orElse(null);
    }

    @Override
    public List<DiscountDto> getAllDiscounts() {
        List<Discount> discounts = discountRepo.findAll();
        List<DiscountDto> discountDtos = new ArrayList<>();
        for (int i = 0; i < discounts.size(); i++) {
            discountDtos.add(convertToDiscountDto(discounts.get(i)));
        }
        return discountDtos;
    }


    @Override
    public DiscountDto convertToDiscountDto(Discount discount) {
        return modelMapper.map(discount, DiscountDto.class);
    }

    @Override
    public Discount convertToDiscount(DiscountDto discountDto) {
        return modelMapper.map(discountDto, Discount.class);
    }
}




