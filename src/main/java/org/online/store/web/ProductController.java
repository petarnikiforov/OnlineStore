package org.online.store.web;

import org.online.store.dto.UserResponse;
import org.online.store.pagination.CustomPage;
import org.online.store.service.ProductService;
import org.online.store.dto.ProductRequest;
import org.online.store.dto.ProductResponse;
import org.online.store.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductService productService;

    private final Integer PAGE_SIZE = 10;
    @PostMapping("")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.postToResponse(productRequest);
        return ResponseEntity.status(201).body(productResponse);
    }
//    @GetMapping("")
//    public ResponseEntity<UserResponse> getById(@PathVariable UUID id){
//        UserResponse userResponse = userService.getToResponse(id);
//        return ResponseEntity.ok(userResponse);
//    }
    @GetMapping("all")
    public CustomPage<ProductResponse> getAllUsers(@RequestParam(required = false, defaultValue = "0") Integer currentPage) {
        Page<ProductResponse> productPage = productService.fetchAll(currentPage, PAGE_SIZE).map(productMapper::modelToResponse);
        return new CustomPage<>(productPage);
    }
//    @DeleteMapping("/{userId}")
//    public void deleteById(@PathVariable UUID userId){
//        userService.deleteById(userId);
//    }
//    @DeleteMapping("all")
//    public void deleteAll(){userService.deleteAll();}
}
