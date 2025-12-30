package org.online.store.service;

import org.online.store.dto.CartDetailsResponse;
import org.online.store.dto.ProductDto;
import org.online.store.mapper.CartMapper;
import org.online.store.mapper.ProductMapper;
import org.online.store.models.Cart;
import org.online.store.models.Product;
import org.online.store.models.Userr;
import org.online.store.repository.CartPagingRepository;
import org.online.store.repository.CartRepository;
import org.online.store.repository.ProductRepository;
import org.online.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    CartPagingRepository cartPagingRepo;
    @Autowired
    UserService userService;
    @Autowired
    ProductMapper productMapper;

    public void putProductInCart(UUID userId, ProductDto productDto){
        Userr user = userService.findById(userId);
        Cart cart = user.getCart();
        List<Product> products = cart.getProducts();
        Product product = productMapper.dtoToModel(productDto);
        products.add(product);
        userService.saveUser(user);
    }
    public CartDetailsResponse getCart(UUID userId){
        Userr user = userService.findById(userId);
        Cart cart = user.getCart();
        return cartMapper.modelToDetailsResponse(cart);
    }
}
