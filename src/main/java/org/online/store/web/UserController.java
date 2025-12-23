package org.online.store.web;

import org.online.store.dto.*;
import org.online.store.mapper.UserMapper;
import org.online.store.pagination.CustomPage;
import org.online.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    private final Integer PAGE_SIZE = 10;
    @PostMapping("")
      public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
          UserResponse userResponse = userService.postToResponse(userRequest);
          return ResponseEntity.status(201).body(userResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id){
          UserResponse userResponse = userService.getToResponse(id);
          return ResponseEntity.ok(userResponse);
    }
    @GetMapping("all")
    public CustomPage<UserResponse> getAllUsers(@RequestParam(required = false, defaultValue = "0") Integer currentPage) {
        Page<UserResponse> userPage = userService.fetchAll(currentPage, PAGE_SIZE).map(userMapper::modelToResponse);
        return new CustomPage<>(userPage);
    }
    @DeleteMapping("/{userId}")
    public void deleteById(@PathVariable UUID userId){
        userService.deleteById(userId);
    }
    @DeleteMapping("all")
    public void deleteAll(){userService.deleteAll();}
    @PatchMapping("/{userId}")
    public void putProductToTheCart(@PathVariable UUID userId, @RequestBody ProductDto productDto){
       userService.putProductInCart(userId, productDto);
    }
    @GetMapping("/{userId}")
    public CartDetailsResponse getCart(@PathVariable UUID userId){
        return userService.getCart(userId);
    }

}
