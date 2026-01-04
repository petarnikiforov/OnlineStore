package org.online.store.repository;

import org.online.store.models.Product;
import org.online.store.models.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<Userr, UUID> {
    Optional<Userr> findByUsername(String username);
}
