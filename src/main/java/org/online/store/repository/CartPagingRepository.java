package org.online.store.repository;

import org.online.store.models.Cart;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CartPagingRepository extends PagingAndSortingRepository<Cart, UUID> {
}
