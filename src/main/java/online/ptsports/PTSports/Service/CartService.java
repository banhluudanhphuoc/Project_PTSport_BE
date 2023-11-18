package online.ptsports.PTSports.Service;


import online.ptsports.PTSports.DTO.CartDto;
import online.ptsports.PTSports.DTO.CartItemDto;
import online.ptsports.PTSports.Entity.Cart;
import online.ptsports.PTSports.Entity.CartItem;

public interface CartService {
    CartDto getCart(Integer userID);

    CartDto addToCart(Integer userID, CartItemDto cartItemDto);

    void deleteCartItem(int userID, int id, int sizeID, int colorID);

    CartItemDto updateCartItem(int userID, int itemID, CartItemDto cartItemDto);

    void deleteCart(int userID);

    int countItem(int userID);

    double totalPrice(int userID);

    CartDto convertToCartDto(Cart cart);

    Cart convertToCart(CartDto cartDto);

    CartItemDto convertToCartItemDto(CartItem cartItem);

    CartItem convertToCartItem(CartItemDto cartItemDto);
}
