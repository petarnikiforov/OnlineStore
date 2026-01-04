package org.online.store.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private ProductResponse product;
    private int qty;
}
