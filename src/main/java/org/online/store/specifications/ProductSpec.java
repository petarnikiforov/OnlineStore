package org.online.store.specifications;
import org.online.store.enums.Category;
import org.online.store.enums.Subcategory;
import org.online.store.models.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpec {
    private static String normalize(String word) {
        word = word.toLowerCase().trim();

        if (word.endsWith("ies")) {
            return word.substring(0, word.length() - 3) + "y";
        }
        if (word.endsWith("es")) {
            return word.substring(0, word.length() - 2);
        }
        if (word.endsWith("s")) {
            return word.substring(0, word.length() - 1);
        }
        return word;
    }
    public static Specification<Product> hasCategory(Category category) {
        return (root, query, cb) ->
                category == null ? cb.conjunction() : cb.equal(root.get("category"), category);
    }
    public static Specification<Product> hasSubcategory(Subcategory subcategory) {
        return (root, query, cb) ->
                subcategory == null ? cb.conjunction() : cb.equal(root.get("subcategory"), subcategory);
    }
    public static Specification<Product> hasPromotion(Boolean promotion) {
        return (root, query, cb) ->
                promotion == null | false ? cb.conjunction() : cb.equal(root.get("hasPromotion"), true);
    }
    public static Specification<Product> newProduct(Boolean newProduct) {
        return (root, query, cb) ->
                newProduct == null | false ? cb.conjunction() : cb.equal(root.get("newProduct"), true);
    }
    public static Specification<Product> isAvailable(Boolean available){
        return (root, query, cb) ->
                available == null ? cb.conjunction() : cb.equal(root.get("availability"), available);
    }
    public static Specification<Product> minPrice(Integer minPrice) {
        return (root, query, cb) ->
                minPrice == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }
    public static Specification<Product> maxPrice(Integer maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
    public static Specification<Product> searchByKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) return cb.conjunction();
            String normalizedKeyword = "%" + normalize(keyword) + "%";
            System.out.println(normalizedKeyword);
            String originalKeyword = "%" + keyword.trim().toLowerCase() + "%";
            return cb.or(cb.like(cb.lower(root.get("name")),normalizedKeyword),
                    cb.like(cb.lower(root.get("description")), originalKeyword));
        };
    }
}
