package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.enums.Gender;
import org.online.store.enums.Role;

import java.util.UUID;
@Data
@Builder
public class UserDetailsResponse {
    private UUID id;
    private String username;
    private String email;
    private String fullName;
    private Gender gender;
    private Role role;
    private UUID cartId;
}
