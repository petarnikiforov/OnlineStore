package org.online.store.repository;

import org.online.store.models.Userr;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface UserPagingRepository extends PagingAndSortingRepository<Userr, UUID> {
}
