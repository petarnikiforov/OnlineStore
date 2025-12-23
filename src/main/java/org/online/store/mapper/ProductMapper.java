package org.online.store.mapper;

import org.mapstruct.Mapper;
import org.online.store.dto.*;
import org.online.store.models.Product;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProductMapper{
    ProductDto requestToDto(ProductRequest productRequest);
    Product dtoToModel(ProductDto productDto);
    ProductResponse modelToResponse(Product product);
    ProductDetailsResponse modelToDetailsResponse(Product product);
}
