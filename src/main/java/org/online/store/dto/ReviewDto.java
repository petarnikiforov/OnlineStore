package org.online.store.dto;

import lombok.Builder;
import lombok.Data;
import org.online.store.models.Product;
import org.online.store.models.Userr;

import java.util.UUID;
@Data
@Builder
public class ReviewDto {
    private UUID id;
    private String comment;
    private int rating;
    private Userr user;
    private Product product;
}
