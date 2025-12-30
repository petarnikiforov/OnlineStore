package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.models.Product;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CartDetailsResponse {
    private UUID cartId;
    private UserResponse user;
    private List<ProductResponse> products;
}
