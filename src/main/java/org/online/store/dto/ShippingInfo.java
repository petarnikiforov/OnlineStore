package org.online.store.dto;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ShippingInfo {
    private String fullName;
    private String phone;
    private String email;
    private String city;
    private String address;
}