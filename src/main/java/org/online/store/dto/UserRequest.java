package org.online.store.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.online.store.enums.Gender;

@Data
@Builder
public class UserRequest {
    @Size(min = 1, max = 10)
    public String username;

    @Size(min = 5, max = 15)
    public String password;
    private String email;
    private String fullName;
    private Gender gender;
}
