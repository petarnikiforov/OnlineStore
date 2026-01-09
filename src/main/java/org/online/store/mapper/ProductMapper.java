package org.online.store.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.online.store.dto.*;
import org.online.store.models.Product;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Mapper
@Component
public interface ProductMapper{
    Product requestToModel(ProductRequest productRequest);
    // MapStruct ще мапне останалите полета автоматично (price, imageUrl, availability...)
    @Mapping(target = "name", expression = "java(resolveName(p, lang))")
    @Mapping(target = "description", expression = "java(resolveDescription(p, lang))")
    ProductResponse modelToResponse(Product p, @Context String lang);

    // ✅ позволено е, защото е default
    default String resolveName(Product p, String lang) {
//        String l = normLang(lang);
        return switch (lang) {
            case "en" -> nvl(p.getNameEn(), p.getNameBg());
            case "de" -> nvl(p.getNameDe(), p.getNameBg());
            default -> p.getNameBg();
        };
    }

    default String resolveDescription(Product p, String lang) {
//        String l = normLang(lang);
        return switch (lang) {
            case "en" -> nvl(p.getDescriptionEn(), p.getDescriptionBg());
            case "de" -> nvl(p.getDescriptionDe(), p.getDescriptionBg());
            default -> p.getDescriptionBg();
        };
    }

//    default String normLang(String lang) {
//        if (lang == null || lang.isBlank()) return "bg";
//        // Accept-Language понякога идва "en-US,en;q=0.9"
//        String first = lang.split(",")[0].trim();
//        return first.length() >= 2 ? first.substring(0,2).toLowerCase(Locale.ROOT) : "bg";
//    }

    default String nvl(String a, String fallback) {
        return (a == null || a.isBlank()) ? fallback : a;
    }
    ProductDetailsResponse modelToDetailsResponse(Product product);
}
