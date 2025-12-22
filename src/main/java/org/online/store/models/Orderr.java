package org.online.store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    // останалите връзки могат да останат засега, но LAZY е по-добре:
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private History history;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cart cart;

    private double totalAmount;
    private LocalDateTime orderDate;
}
