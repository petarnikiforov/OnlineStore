package org.online.store.repository;

import org.online.store.models.Cart;
import org.online.store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

}
