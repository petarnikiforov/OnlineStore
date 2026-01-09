package org.online.store.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.online.store.enums.Category;
import org.online.store.enums.Subcategory;

import java.util.List;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private UUID id;
    private String name;
    private String description;
    private float oldPrice;
    private float price;
    private String imageUrl;
    private boolean availability;
    private boolean hasPromotion;
    private boolean newProduct;
    private Category category;
    private Subcategory subcategory;
}
