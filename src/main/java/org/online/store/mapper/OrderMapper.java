package org.online.store.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.online.store.dto.*;
import org.online.store.models.OrderItem;
import org.online.store.models.Orderr;

import java.util.Locale;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Orderr dtoToModel(OrderDto orderDto);

    OrderResponse modelToResponse(Orderr order);

    OrderDetailsResponse modelToDetailsResponse(Orderr order, @Context String lang);

    @Mapping(target = "productName", expression = "java(resolveItemName(item, lang))")
    OrderItemResponse itemToResponse(OrderItem item, @Context String lang);

    default String resolveItemName(OrderItem item, String lang) {
        if (item == null) return null;
        String l = norm(lang);
        return switch (l) {
            case "en" -> nvl(item.getProductNameEn(), item.getProductNameBg());
            case "de" -> nvl(item.getProductNameDe(), item.getProductNameBg());
            default -> item.getProductNameBg();
        };
    }

    default String norm(String lang) {
        if (lang == null || lang.isBlank()) return "bg";
        String first = lang.split(",")[0].trim(); // "en-US,en;q=0.9"
        return first.length() >= 2 ? first.substring(0, 2).toLowerCase(Locale.ROOT) : "bg";
    }

    default String nvl(String a, String fallback) {
        return (a == null || a.isBlank()) ? fallback : a;
    }
}
