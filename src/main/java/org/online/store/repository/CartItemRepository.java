package org.online.store.repository;

import org.online.store.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Optional<CartItem> findByCart_IdAndProduct_Id(UUID cartId, UUID productId);
    void deleteByCart_IdAndProduct_Id(UUID cartId, UUID productId);
    void deleteByCart_Id(UUID cartId);
}
