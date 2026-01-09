package org.online.store.service;

import org.online.store.dto.OrderDetailsResponse;
import org.online.store.dto.ShippingInfo;
import org.online.store.dto.OrderResponse;
import org.online.store.enums.OrderStatus;
import org.online.store.error.NotFoundObjectException;
import org.online.store.mapper.OrderMapper;
import org.online.store.models.*;
import org.online.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepo;

    @Autowired
    UserService userService;

    @Autowired
    OrderMapper orderMapper;

    public OrderResponse checkout(UUID userId, ShippingInfo request) {
        Userr user = userService.findById(userId);
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getItems();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is Empty");
        }

        Orderr order = Orderr.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.ORDERED)
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .city(request.getCity())
                .address(request.getAddress())
                .build();
        double total = 0.0;

        for(CartItem cartItem : cartItems){
            Product p = cartItem.getProduct();
            int qty = cartItem.getQty();


            float unitPrice = p.getPrice();
            double lineTotal = unitPrice * qty;

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productId(p.getId())
                    .productNameBg(p.getNameBg())
                    .productNameEn(p.getNameEn())
                    .productNameDe(p.getNameDe())
                    .imageUrl(p.getImageUrl())
                    .unitPrice(unitPrice)
                    .quantity(qty)
                    .lineTotal(lineTotal)
                    .build();

            order.getProducts().add(orderItem);
            total += lineTotal;
        }
        order.setTotalAmount(total);
        Orderr savedOrder = orderRepo.save(order);

        cartItems.clear();
        userService.saveUser(user);

        return orderMapper.modelToResponse(savedOrder);
    }

    public void validateShipping(ShippingInfo s) {
        if (s == null) throw new IllegalStateException("Shipping info is required");
        if (isBlank(s.getFullName())) throw new IllegalStateException("Full name is required");
        if (isBlank(s.getPhone())) throw new IllegalStateException("Phone is required");
        if (isBlank(s.getEmail())) throw new IllegalStateException("Email is required");
        if (isBlank(s.getCity())) throw new IllegalStateException("City is required");
        if (isBlank(s.getAddress())) throw new IllegalStateException("Address is required");
    }
    private boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }

    public List<OrderResponse> getMyOrders(UUID userId) {
        userService.findById(userId); // validate user exists
        return orderRepo.findAllByUser_IdOrderByOrderDateDesc(userId)
                .stream()
                .map(orderMapper::modelToResponse)
                .toList();
    }

    public OrderDetailsResponse getMyOrderDetails(UUID userId, UUID orderId, String lang) {
        Orderr order = findById(orderId);

        if (!order.getUser().getId().equals(userId)) {
            throw new IllegalStateException("Order does not belong to this user");
        }

        return orderMapper.modelToDetailsResponse(order, lang);
    }

    public OrderResponse cancel(UUID userId, UUID orderId) {
        Orderr order = findById(orderId);

        if (!order.getUser().getId().equals(userId)) {
            throw new IllegalStateException("Order does not belong to this user");
        }

        if (order.getStatus() != OrderStatus.ORDERED) {
            throw new IllegalStateException("Only ORDERED orders can be canceled");
        }

        order.setStatus(OrderStatus.CANCELED);
        Orderr saved = orderRepo.save(order);
        return orderMapper.modelToResponse(saved);
    }

    public Orderr findById(UUID id) {
        return orderRepo.findById(id).orElseThrow(() ->
                new NotFoundObjectException("Order not found!", Orderr.class.getName(), id.toString())
        );
    }

    /**
     * Автоматични промени:
     * ORDERED -> SHIPPED след 12 часа
     * SHIPPED -> DELIVERED след 24 часа (реално 24 часа от orderDate)
     *
     * Пускаме го на всеки 5 минути (можеш да го смениш).
     */
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void autoUpdateStatuses() {
        LocalDateTime now = LocalDateTime.now();

        // ORDERED -> SHIPPED (>= 12h)
        LocalDateTime orderedThreshold = now.minusHours(12);
        List<Orderr> toShip = orderRepo.findAllByStatusAndOrderDateBefore(OrderStatus.ORDERED, orderedThreshold);
        for (Orderr o : toShip) {
            // ако е cancel-ната междувременно — пропускаме
            if (o.getStatus() == OrderStatus.ORDERED) {
                o.setStatus(OrderStatus.SHIPPED);
                orderRepo.save(o);
            }
        }

        // SHIPPED -> DELIVERED (>= 24h)
        LocalDateTime shippedThreshold = now.minusHours(24);
        List<Orderr> toDeliver = orderRepo.findAllByStatusAndOrderDateBefore(OrderStatus.SHIPPED, shippedThreshold);
        for (Orderr o : toDeliver) {
            if (o.getStatus() == OrderStatus.SHIPPED) {
                o.setStatus(OrderStatus.DELIVERED);
                orderRepo.save(o);
            }
        }
    }

}
