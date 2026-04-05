package com.swiftmart.cart_service.service.impl;

import com.swiftmart.cart_service.dto.AddToCartRequest;
import com.swiftmart.cart_service.dto.CartItemDto;
import com.swiftmart.cart_service.dto.CartResponse;
import com.swiftmart.cart_service.entity.Cart;
import com.swiftmart.cart_service.entity.CartItem;
import com.swiftmart.cart_service.exception.CartException;
import com.swiftmart.cart_service.exception.ItemNotFoundException;
import com.swiftmart.cart_service.client.ProductClient;
import com.swiftmart.cart_service.client.dto.ProductResponse;
import com.swiftmart.cart_service.repository.CartRepository;
import com.swiftmart.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;
    @Override
    public CartResponse addToCart(Long userId, AddToCartRequest request) {
        Cart cart=cartRepository.findByUserId(userId)
                .orElseGet(()->creatNewCart(userId));

        ProductResponse product=productClient.getProduct(request.getProductId());

        if (product == null ) {
            throw new RuntimeException("Product not available");
        }
        Optional< CartItem> existingItem=cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst();

        if(existingItem.isPresent()){
            CartItem item=existingItem.get();
            item.setQuantity(item.getQuantity()+request.getQuantity());
        }else{
            CartItem newItem=new CartItem();
            newItem.setProductId(request.getProductId());
            newItem.setQuantity(request.getQuantity());
            newItem.setPrice(product.getPrice());
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }
        updateTotalPrice(cart);
        cart.setUpdatedAt(LocalDateTime.now());
        Cart savedCart=cartRepository.save(cart);
        return mapToResponse(savedCart);
    }




    @Override
    public CartResponse removeFromCart(Long userId, Long productId) {
        return null;
    }

    @Override
    public CartResponse getCart(Long userId) {
        return mapToResponse( cartRepository.findByUserId(userId)
                .orElseThrow(()-> new CartException("cart is Empty")));
    }

    @Override
    public CartResponse increaseQuantity(Long userId, Long productId) {
        Cart cart=cartRepository.findByUserId(userId)
                .orElseThrow(()-> new CartException("cart is Empty"));

        CartItem cartItem=findItem(cart,productId);
        cartItem.setQuantity(cartItem.getQuantity()+1);

        updateTotalPrice(cart);
        return mapToResponse(cartRepository.save(cart));
    }

    @Override
    public  CartResponse decreaseQuantity(Long userId, Long productId) {
        Cart cart=cartRepository.findByUserId(userId)
                .orElseThrow(()-> new CartException("cart is Empty"));
        CartItem cartItem=findItem(cart,productId);
        int quantity=cartItem.getQuantity()-1;
        if(quantity<1){
            cart.getItems().remove(cartItem);
        }else{
            cartItem.setQuantity(quantity);
        }
        updateTotalPrice(cart);
        return mapToResponse(cartRepository.save(cart));
    }



    private Cart creatNewCart(Long userId) {
        Cart cart=new Cart();
        cart.setUserId(userId);
        cart.setCreatedAt(LocalDateTime.now());
       return cart;
    }

    private void updateTotalPrice(Cart cart) {

        double total= cart.getItems().stream()
                .mapToDouble(item->item.getPrice()* item.getQuantity())
                .sum();
        cart.setTotal_price(total);
    }

    private CartResponse mapToResponse(Cart cart) {

        List<CartItemDto> items=cart.getItems().stream()
                .map(item->new CartItemDto(
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice()
                )).toList();
        return new CartResponse(cart.getUserId(),items,cart.getTotal_price());
    }

    private CartItem findItem(Cart cart,Long productId ){
        return cart.getItems().stream()
                .filter(item->item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(()->new ItemNotFoundException("Item Not Found"));
    }
}
