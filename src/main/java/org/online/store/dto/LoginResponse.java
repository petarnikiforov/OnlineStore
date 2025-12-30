package org.online.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.online.store.dto.UserResponse;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UserResponse user;
}
