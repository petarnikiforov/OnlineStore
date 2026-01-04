package org.online.store.web;

import org.online.store.dto.ProductDetailsResponse;
import org.online.store.dto.UserResponse;
import org.online.store.enums.Category;
import org.online.store.enums.Subcategory;
import org.online.store.pagination.CustomPage;
import org.online.store.service.ProductService;
import org.online.store.dto.ProductRequest;
import org.online.store.dto.ProductResponse;
import org.online.store.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

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
    @PostMapping(value = "/admin/import-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importCsv(@RequestParam("file") MultipartFile file) {
        int created = productService .importFromCsv(file);
        return ResponseEntity.ok(Map.of("created", created));
    }
    @GetMapping("{productId}")
    public ResponseEntity<ProductDetailsResponse> getById(@PathVariable UUID productId){
        ProductDetailsResponse product = productService.getToDetailsResponse(productId);
        return ResponseEntity.ok(product);
    }
    @GetMapping("all")
    public CustomPage<ProductResponse> getAllProducts(@RequestParam(required = false, defaultValue = "0") Integer currentPage) {
        Page<ProductResponse> productPage = productService.fetchAll(currentPage, PAGE_SIZE).map(productMapper::modelToResponse);
        return new CustomPage<>(productPage);
    }
    @GetMapping
    public ResponseEntity<CustomPage<ProductResponse>> getProductsByFilters(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Subcategory subcategory,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) Boolean promotion,
            @RequestParam(required = false) Boolean newProduct,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "name,asc") String sort){
        Pageable pageable = PageRequest.of(page, size);
        System.out.println("breakPointController");
        return ResponseEntity.ok(productService.getByFilter(keyword,
                category, subcategory, available, promotion, newProduct, minPrice, maxPrice, pageable));
    }
    @DeleteMapping("admin/{productId}")
    public void deleteById(@PathVariable UUID productId){
        productService.deleteById(productId);
    }
    @DeleteMapping("admin/all")
    public void deleteAll(){productService.deleteAll();}
}
