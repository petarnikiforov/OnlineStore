package org.online.store.service;

import org.online.store.dto.*;
import org.online.store.error.NotFoundObjectException;
import org.online.store.mapper.ProductMapper;
import org.online.store.models.Product;
import org.online.store.repository.ProductPagingRepository;
import org.online.store.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        ProductDto productDto = productMapper.requestToDto(productRequest);
        Product product = productMapper.dtoToModel(productDto);
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


}
