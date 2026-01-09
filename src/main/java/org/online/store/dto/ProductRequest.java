package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.enums.Category;
import org.online.store.enums.Subcategory;

import java.util.UUID;
@Data
@Builder
public class ProductRequest {
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
    private boolean newProduct;
    private Category category;
    private Subcategory subcategory;
}
