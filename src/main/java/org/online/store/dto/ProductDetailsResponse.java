package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.enums.Category;
import org.online.store.enums.Subcategory;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ProductDetailsResponse {
    private UUID id;
    private String nameBg;
    private String nameEn;
    private String nameDe;
    private String descriptionBg;
    private String descriptionEn;
    private String descriptionDe;
    private float oldPrice;
    private float price;
    private String imageUrl;
    private boolean availability;
    private boolean hasPromotion;
    private boolean isNew;
    private Category category;
    private Subcategory subcategory;
}
