package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.models.Product;
import org.online.store.models.Userr;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderRequest {
    private List<Product> products;
    private Userr user;
    private double totalAmount;
    private LocalDateTime orderDate;
}
