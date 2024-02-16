package org.online.store.repository;

import org.online.store.models.Userr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepository extends CrudRepository<Userr, UUID> {

}
