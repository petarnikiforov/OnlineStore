package org.online.store.dto;
import lombok.Data;
import org.online.store.models.Orderr;

import java.util.UUID;

@Data
public class OrderItemResponse {
    private UUID productId;
    private String productName;
    private String imageUrl;
    private double unitPrice;
    private int quantity;
    private double lineTotal;
}
