package org.online.store.repository;

import org.online.store.models.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ProductPagingRepository extends PagingAndSortingRepository<Product, UUID> {
}
