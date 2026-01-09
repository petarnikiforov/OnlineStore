package org.online.store.service;

import org.online.store.dto.*;
import org.online.store.mapper.ProductMapper;
import org.online.store.models.Cart;
import org.online.store.models.CartItem;
import org.online.store.models.Product;
import org.online.store.models.Userr;
import org.online.store.repository.CartItemRepository;
import org.online.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    ProductRepository productRepo;

    @Autowired
    CartItemRepository cartItemRepo;

    @Autowired
    UserService userService;

    @Autowired
    ProductMapper productMapper;

    // ✅ запазваме името
    @Transactional
    public void putProductInCart(UUID userId, CartRequest req) {
        Userr user = userService.findById(userId);
        Cart cart = user.getCart();

        if (req.getProductId() == null) {
            throw new IllegalArgumentException("productId is required");
        }

        int quantity = req.getQuantity();
        if (quantity <= 0) quantity = 1;

        Product product = productRepo.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + req.getProductId()));

        CartItem item = cartItemRepo.findByCart_IdAndProduct_Id(cart.getId(), product.getId()).orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQty(quantity);

            // ⚠️ важно: Cart трябва да има OneToMany към CartItem
            cart.getItems().add(item);
        } else {
            item.setQty(item.getQty() + quantity);
        }
    }

    // ✅ getCart връща твоето CartDetailsResponse { cartId, user, products: [CartItemDto] }
    @Transactional(readOnly = true)
    public CartDetailsResponse getCart(UUID userId, String lang) {
        Userr user = userService.findById(userId);
        Cart cart = user.getCart();

        var out = new ArrayList<CartItemDto>();

        for (CartItem it : cart.getItems()) {
            CartItemDto dto = new CartItemDto();
            dto.setProduct(productMapper.modelToResponse(it.getProduct(), lang)); // или modelToDto според mapper-а ти
            dto.setQty(it.getQty());
            out.add(dto);
        }

        return CartDetailsResponse.builder()
                .id(cart.getId())
                .user(/* тук: mapper към UserResponse или null ако не ти трябва */ null)
                .products(out)
                .build();
    }

    // ✅ намалява qty
    @Transactional
    public void decrease(UUID userId, CartChangeRequest req) {
        UUID productId = req.getProductId();
        int quantity = req.getQuantity();
        if (quantity <= 0) quantity = 1;

        Userr user = userService.findById(userId);
        Cart cart = user.getCart();

        CartItem item = cartItemRepo.findByCart_IdAndProduct_Id(cart.getId(), productId).orElse(null);
        if (item == null) return;

        int next = item.getQty() - quantity;
        if (next <= 0) {
            cart.getItems().remove(item);
            cartItemRepo.delete(item);
        } else {
            item.setQty(next);
        }
    }


    // ✅ премахва продукта изцяло
    @Transactional
    public void removeProductFromCart(UUID userId, UUID productId) {
        Userr user = userService.findById(userId);
        Cart cart = user.getCart();

        CartItem item = cartItemRepo.findByCart_IdAndProduct_Id(cart.getId(), productId).orElse(null);
        if (item == null) return;

        cart.getItems().remove(item);
        cartItemRepo.delete(item);
    }

    // ✅ clear
    @Transactional
    public void clearCart(UUID userId) {
        Userr user = userService.findById(userId);
        Cart cart = user.getCart();

        cartItemRepo.deleteByCart_Id(cart.getId());
        cart.getItems().clear();
    }
}
