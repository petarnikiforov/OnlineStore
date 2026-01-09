package org.online.store.dto;

import lombok.Data;
import org.online.store.enums.Gender;
@Data
public class UserUpdateRequest {
    private String username;
    private String email;
    private String fullName;
    private Gender gender;

}
