package org.online.store.service;

import org.online.store.dto.*;
import org.online.store.enums.Category;
import org.online.store.enums.Subcategory;
import org.online.store.error.NotFoundObjectException;
import org.online.store.mapper.ProductMapper;
import org.online.store.models.Product;
import org.online.store.pagination.CustomPage;
import org.online.store.repository.ProductPagingRepository;
import org.online.store.repository.ProductRepository;
import org.online.store.specifications.ProductSpec;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ProductService {

    @Autowired
    ProductRepository productRepo;
    @Autowired
    ProductPagingRepository productPagingRepo;
    @Autowired
    ProductMapper productMapper;
    public void saveProduct(Product product){
        productRepo.save(product);
    }
    public void deleteById(UUID id){
        productRepo.delete(findById(id));
    }
    public void deleteAll(){
        productRepo.deleteAll();
    }

    public Product findById(UUID id){
        return productRepo.findById(id).orElseThrow(() -> {
            throw new NotFoundObjectException("Product not found!",Product.class.getName(), id.toString());});
    }

    public Page<Product> fetchAll(int currentPage, int PageSize){
        return productPagingRepo.findAll(PageRequest.of(currentPage,PageSize));
    }
    public ProductResponse postToResponse(ProductRequest productRequest){
        System.out.println("Req" + productRequest);
        ProductDto productDto = productMapper.requestToDto(productRequest);
        System.out.println("DTO" + productDto);
        Product product = productMapper.dtoToModel(productDto);
        System.out.println("Product" + productDto);
        saveProduct(product);
        ProductResponse productResponse = productMapper.modelToResponse(product);
        return productResponse;
    }
    public ProductResponse getToResponse(UUID id) {
        Product product = findById(id);
        return productMapper.modelToResponse(product);
    }
    public ProductDetailsResponse getToDetailsResponse(UUID id) {
        Product product = findById(id);
        return productMapper.modelToDetailsResponse(product);
    }
    public ProductResponse updateFields(UUID id, ProductDto productDto){
        Product existingProduct = findById(id);
        BeanUtils.copyProperties(productDto, existingProduct);
        return productMapper.modelToResponse(existingProduct);
    }
    public CustomPage<ProductResponse> getByFilter(String keyword, Category category,
                                                   Subcategory subcategory, Boolean available, Boolean promotion,
                                                   Boolean newProduct, Integer minPrice, Integer maxPrice, Pageable pageable){
        Specification<Product> spec = Specification
                .where(ProductSpec.hasCategory(category))
                .and(ProductSpec.hasSubcategory(subcategory))
                .and(ProductSpec.isAvailable(available))
                .and(ProductSpec.hasPromotion(promotion))
                .and(ProductSpec.newProduct(newProduct))
                .and(ProductSpec.minPrice(minPrice))
                .and(ProductSpec.maxPrice(maxPrice))
                .and(ProductSpec.searchByKeyword(keyword));
        Page<ProductResponse> page = productRepo
                .findAll(spec, pageable)
                .map(productMapper::modelToResponse);
        return new CustomPage<>(page);
    }
}
