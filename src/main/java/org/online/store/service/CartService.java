package org.online.store.service;

import org.online.store.mapper.CartMapper;
import org.online.store.repository.CartPagingRepository;
import org.online.store.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    CartPagingRepository cartPagingRepo;


}
