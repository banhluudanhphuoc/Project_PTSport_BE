package online.ptsports.PTSports.Service;


import online.ptsports.PTSports.DTO.WishListDto;
import online.ptsports.PTSports.Entity.WishList;

public interface WishListService {
    WishListDto getWishListByUser(Integer userID);

    WishListDto addToWishList(Integer userID, Integer productID);

    WishListDto convertToWishListDto(WishList wishList);

    WishList convertToWishList(WishListDto wishListDto);
}
