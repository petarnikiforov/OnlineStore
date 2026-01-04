package org.online.store.repository;

import org.online.store.enums.OrderStatus;
import org.online.store.models.Orderr;
import org.online.store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<Orderr, UUID> {
    List<Orderr> findAllByUser_IdOrderByOrderDateDesc(UUID userId);
    List<Orderr> findAllByStatusAndOrderDateBefore(OrderStatus status, LocalDateTime threshold);
}
