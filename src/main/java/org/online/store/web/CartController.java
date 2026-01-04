package org.online.store.web;

import org.online.store.dto.CartChangeRequest;
import org.online.store.dto.CartDetailsResponse;
import org.online.store.dto.CartRequest;
import org.online.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    // ✅ запазваме го
    @PatchMapping("/{userId}")
    public void putProductToTheCart(@PathVariable UUID userId, @RequestBody CartRequest req) {
        cartService.putProductInCart(userId, req);
    }

    @PatchMapping("/{userId}/decrease")
    public void decrease(@PathVariable UUID userId, @RequestBody CartChangeRequest req) {
        cartService.decrease(userId, req);
    }

    // ✅ запазваме го
    @GetMapping("/{userId}")
    public CartDetailsResponse getCart(@PathVariable UUID userId) {
        return cartService.getCart(userId);
    }

    // ✅ НОВО: маха продукт от количката
    @DeleteMapping("/{userId}/{productId}")
    public void removeProduct(@PathVariable UUID userId, @PathVariable UUID productId) {
        cartService.removeProductFromCart(userId, productId);
    }

    // ✅ НОВО: чисти количката
    @DeleteMapping("/{userId}")
    public void clear(@PathVariable UUID userId) {
        cartService.clearCart(userId);
    }
}
