package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.enums.Category;

import java.util.UUID;

@Data
@Builder
public class ProductDto {
        private UUID id;
        private String name;
        private String description;
        private float price;
        private String imageUrl;
        private boolean availability;
        private Category category;
}
