package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.enums.Gender;

import java.util.UUID;
@Data
@Builder
public class UserDetailsResponse {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Gender gender;
}
