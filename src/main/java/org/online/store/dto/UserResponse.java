package org.online.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import org.online.store.enums.Gender;
import org.online.store.models.Cart;
import org.online.store.models.History;

import java.util.UUID;
@Data
@Builder
public class UserResponse {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Gender gender;
    private History history;
    private Cart cart;
}
