package org.online.store.dto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CartResponse {
    private UUID cartId;
    private UserResponse user;
    private List<ProductResponse> products;
}
