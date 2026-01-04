package org.online.store.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.online.store.enums.Gender;
import org.online.store.enums.Role;

@Data
@Builder
public class UserDto {
    public String username;
    public String password;
    private String email;
    private String fullName;
    private Gender gender;
    private Role role;
}
