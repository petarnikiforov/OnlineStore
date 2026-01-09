package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.models.Userr;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderDetailsResponse {
    private UUID id;
    private List<OrderItemResponse> products;
    private double totalAmount;
    private String fullName;
    private String phone;
    private String email;
    private String city;
    private String address;
    private LocalDateTime orderDate;
}