package org.online.store.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    private Orderr order;

    // snapshot на продукта към момента на покупката
    private UUID productId;
    private String productNameBg;
    private String productNameEn;
    private String productNameDe;
    private String imageUrl;
    private float unitPrice;
    private int quantity;
    private double lineTotal;
}

