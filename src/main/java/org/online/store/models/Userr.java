package org.online.store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.online.store.enums.Category;
import org.online.store.enums.Gender;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Userr {
    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Gender gender;
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    @Builder.Default
//    private History history = new History();
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonIgnore
//    private List<Orderr> orders;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private Cart cart = new Cart();

}
