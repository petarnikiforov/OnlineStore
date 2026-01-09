package org.online.store.web;

import org.online.store.dto.OrderDetailsResponse;
import org.online.store.dto.OrderResponse;
import org.online.store.dto.ShippingInfo;
import org.online.store.repository.OrderRepository;
import org.online.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepo;

    @PostMapping("checkout/{userId}")
    public ResponseEntity<OrderResponse> checkout(@PathVariable UUID userId,
                                                  @RequestBody ShippingInfo shippingInfo){
        orderService.validateShipping(shippingInfo);
        OrderResponse created = orderService.checkout(userId, shippingInfo);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/my/{userId}")
    public List<OrderResponse> myOrders(@PathVariable UUID userId) {
        return orderService.getMyOrders(userId);
    }
    // GET /orders/my/{userId}/{orderId}
    @GetMapping("/my/{userId}/{orderId}")
    public OrderDetailsResponse myOrderDetails(@PathVariable UUID userId,
                                               @PathVariable UUID orderId,
                                               @RequestHeader(value = "Accept-Language", required = false) String lang) {
        return orderService.getMyOrderDetails(userId, orderId, lang);
    }

    // PATCH /orders/my/{userId}/{orderId}/cancel
    @PatchMapping("/my/{userId}/{orderId}/cancel")
    public OrderResponse cancel(@PathVariable UUID userId,
                                @PathVariable UUID orderId) {
        return orderService.cancel(userId, orderId);
    }
}
