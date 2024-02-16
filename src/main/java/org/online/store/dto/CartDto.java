package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.models.Product;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CartDto {
    private UUID userId;
    private List<Product> products;
}
