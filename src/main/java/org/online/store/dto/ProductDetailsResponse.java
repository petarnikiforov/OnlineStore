package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.enums.Category;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ProductDetailsResponse {
    private UUID id;
    private String name;
    private String description;
    private float price;
    private String imageUrl;
    private boolean availability;
    private Category category;
//    private List<ReviewResponse> reviews;
//    private OrderResponse order;
}
