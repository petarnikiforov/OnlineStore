package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.models.Product;
import org.online.store.models.Userr;

@Data
@Builder
public class ReviewRequest {
    private String comment;
    private int rating;
    private Userr user;
    private Product product;
}
