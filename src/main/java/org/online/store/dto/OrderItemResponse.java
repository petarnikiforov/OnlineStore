package org.online.store.dto;
import org.online.store.models.Orderr;

import java.util.UUID;

public class OrderItemResponse {
    private UUID id;
    private Orderr order;
    private UUID productId;
    private String productName;
    private String imageUrl;
    private float price;
    private int quantity;
    private double lineTotal;
}
