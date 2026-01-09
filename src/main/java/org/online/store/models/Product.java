package org.online.store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.online.store.enums.Category;
import org.online.store.enums.Subcategory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String nameBg;
    @Column(nullable = false)
    private String nameEn;
    @Column(nullable = false)
    private String nameDe;
    private String descriptionBg;
    private String descriptionEn;
    private String descriptionDe;
    private float price;
    private float oldPrice;
    @Column(name = "image_url", length = 2048)
    private String imageUrl;
    private boolean availability;
    private boolean hasPromotion;
    private boolean newProduct;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Subcategory subcategory;
}
