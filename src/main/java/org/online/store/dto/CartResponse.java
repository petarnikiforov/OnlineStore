package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.models.Orderr;
import org.online.store.models.Product;
import org.online.store.models.Userr;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CartResponse {
    private UUID cartId;
    private Userr user;
    private Orderr order;
}
