package org.online.store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.online.store.dto.ShippingInfo;
import org.online.store.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Orderr {
    @Id
    @GeneratedValue
    private UUID id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<OrderItem> products = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Userr user;
    private double totalAmount;
    private LocalDateTime orderDate;
    private String fullName;
    private String phone;
    private String email;
    private String city;
    private String address;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
