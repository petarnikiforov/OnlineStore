package org.online.store.service;

import org.online.store.dto.*;
import org.online.store.error.NotFoundObjectException;
import org.online.store.mapper.CartMapper;
import org.online.store.mapper.UserMapper;
import org.online.store.models.Cart;
import org.online.store.models.Orderr;
import org.online.store.models.Product;
import org.online.store.models.Userr;
import org.online.store.repository.UserPagingRepository;
import org.online.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;
    @Autowired
    UserPagingRepository userPagingRepo;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductService productService;

    public void saveUser(Userr user){
        userRepo.save(user);
    }
    public void deleteById(UUID id){
        userRepo.delete(findById(id));
    }
    public void deleteAll(){
        userRepo.deleteAll();
    }

    public Userr findById(UUID id){
       return userRepo.findById(id).orElseThrow(() -> {
           throw new NotFoundObjectException("User not found!",Userr.class.getName(), id.toString());});
    }

    public Page<Userr> fetchAll(int currentPage, int PageSize){
        return userPagingRepo.findAll(PageRequest.of(currentPage,PageSize));
    }
    public UserResponse postToResponse(UserRequest userRequest){
        UserDto userDto = userMapper.requestToDto(userRequest);
        Userr user = userMapper.dtoToModel(userDto);
        saveUser(user);
        UserResponse userResponse = userMapper.modelToResponse(user);
        return userResponse;
    }
    public UserResponse getToResponse(UUID id) {
        Userr user = findById(id);
        return userMapper.modelToResponse(user);
    }
    public CartResponse putProductsInCart(UUID userId, ProductIdsDto productIdsDto){
        Userr user = findById(userId);
        Cart cart = user.getCart();
        Orderr order = cart.getOrder();
        if(order == null){
            System.out.println("ORDERAAAA E NULLL !!!!");
        }
        List<Product> existingProducts = order.getProducts();
        List<String> productIds = productIdsDto.getProductIds();
        for(String Id : productIds){
            existingProducts.add(productService.findById(UUID.fromString(Id)));
        }
        saveUser(user);
        return cartMapper.modelToResponse(cart);
    }

}
