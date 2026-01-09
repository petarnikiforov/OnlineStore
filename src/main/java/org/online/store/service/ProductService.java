package org.online.store.service;

import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.online.store.dto.*;
import org.online.store.enums.Category;
import org.online.store.enums.Subcategory;
import org.online.store.error.NotFoundObjectException;
import org.online.store.mapper.ProductMapper;
import org.online.store.models.Product;
import org.online.store.pagination.CustomPage;
import org.online.store.repository.*;
import org.online.store.specifications.ProductSpec;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class ProductService {

    @Autowired
    ProductRepository productRepo;
    @Autowired
    CartItemRepository cartItemRepo;
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    ProductPagingRepository productPagingRepo;
    @Autowired
    ProductMapper productMapper;
    public void saveProduct(Product product){
        productRepo.save(product);
    }
    @Transactional
    public void deleteById(UUID id){
        System.out.println("Преди CartItem Delete");
        cartItemRepo.deleteByProduct_Id(id);
        System.out.println("След CartItem Delete");
        productRepo.delete(findById(id));
    }
    public void deleteAll(){
        productRepo.deleteAll();
    }

    public Product findById(UUID id){
        return productRepo.findById(id).orElseThrow(() -> {
            throw new NotFoundObjectException("Product not found!",Product.class.getName(), id.toString());});
    }

    public Page<Product> fetchAll(int currentPage, int PageSize){
        return productPagingRepo.findAll(PageRequest.of(currentPage,PageSize));
    }
    public ProductResponse postToResponse(ProductRequest productRequest, String lang){
        Product product = productMapper.requestToModel(productRequest);
        saveProduct(product);
        ProductResponse productResponse = productMapper.modelToResponse(product, lang);
        return productResponse;
    }
    public ProductResponse getToResponse(UUID id, String lang) {
        Product product = findById(id);
        return productMapper.modelToResponse(product, lang);
    }
    public ProductDetailsResponse getToDetailsResponse(UUID id) {
        Product product = findById(id);
        return productMapper.modelToDetailsResponse(product);
    }
    public CustomPage<ProductResponse> getByFilter(String keyword, Category category,
                                                   Subcategory subcategory, Boolean available, Boolean promotion,
                                                   Boolean newProduct, Integer minPrice, Integer maxPrice, Pageable pageable, String lang){
        Specification<Product> spec = Specification
                .where(ProductSpec.hasCategory(category))
                .and(ProductSpec.hasSubcategory(subcategory))
                .and(ProductSpec.isAvailable(available))
                .and(ProductSpec.hasPromotion(promotion))
                .and(ProductSpec.newProduct(newProduct))
                .and(ProductSpec.minPrice(minPrice))
                .and(ProductSpec.maxPrice(maxPrice))
                .and(ProductSpec.searchByKeyword(keyword));
        Page<ProductResponse> page = productRepo
                .findAll(spec, pageable)
                .map(p -> productMapper.modelToResponse(p, lang));
        return new CustomPage<>(page);
    }

    public ProductDetailsResponse getForEdit(UUID id) {
        Product p = findById(id);
        return ProductDetailsResponse.builder()
                .id(p.getId())
                .nameBg(p.getNameBg())
                .nameEn(p.getNameEn())
                .nameDe(p.getNameDe())
                .descriptionBg(p.getDescriptionBg())
                .descriptionEn(p.getDescriptionEn())
                .descriptionDe(p.getDescriptionDe())
                .imageUrl(p.getImageUrl())
                .price(p.getPrice())
                .oldPrice(p.getOldPrice())
                .availability(p.isAvailability())
                .hasPromotion(p.isHasPromotion())
                .isNew(p.isNewProduct())
                .category(p.getCategory())
                .subcategory(p.getSubcategory())
                .build();
    }

    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest req, String lang) {
        Product existing = findById(id);

        existing.setNameBg(req.getNameBg());
        existing.setNameEn(req.getNameEn());
        existing.setNameDe(req.getNameDe());

        existing.setDescriptionBg(req.getDescriptionBg());
        existing.setDescriptionEn(req.getDescriptionEn());
        existing.setDescriptionDe(req.getDescriptionDe());

        existing.setPrice(req.getPrice());
        existing.setOldPrice(req.getOldPrice());
        existing.setImageUrl(req.getImageUrl());

        existing.setAvailability(req.isAvailability());
        existing.setNewProduct(req.isNewProduct());

        boolean promoAuto = req.getOldPrice() > 0 && req.getOldPrice() > req.getPrice();
        existing.setHasPromotion(promoAuto);

        existing.setCategory(req.getCategory());
        existing.setSubcategory(req.getSubcategory());

        Product saved = productRepo.save(existing);
        return productMapper.modelToResponse(saved, lang);
    }

    @Transactional
    public int importFromCsv(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("CSV файлът е празен.");
        }

        try (
                var reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)
                );
                CSVParser parser = CSVFormat.DEFAULT
                        .builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .setTrim(true)
                        .build()
                        .parse(reader)
        ) {
            List<Product> products = new ArrayList<>();

            for (CSVRecord r : parser) {
                Product p = new Product();
                p.setNameBg(req(r, "nameBg"));
                p.setNameEn(req(r, "nameEn"));
                p.setNameDe(req(r, "nameDe"));
                p.setDescriptionBg(req(r, "descriptionBg"));
                p.setDescriptionEn(req(r, "descriptionEn"));
                p.setDescriptionDe(req(r, "descriptionDe"));
                p.setImageUrl(req(r, "imageUrl"));

                float price = parseFloat(req(r, "price"));
                float oldPrice = parseFloat(opt(r, "oldPrice", "0"));

                p.setPrice(price);
                p.setOldPrice(oldPrice);

                p.setAvailability(parseBool(opt(r, "availability", "true")));
                p.setHasPromotion(parseBool(opt(r, "hasPromotion", "false")));
                p.setNewProduct(parseBool(opt(r, "newProduct", "false")));

                p.setCategory(Category.valueOf(req(r, "category").toUpperCase()));
                p.setSubcategory(Subcategory.valueOf(req(r, "subcategory").toUpperCase()));

                // Автоматична промоция, ако oldPrice > price
                if (oldPrice > 0 && oldPrice > price) {
                    p.setHasPromotion(true);
                }
                System.out.println(p.getImageUrl());
                products.add(p);
            }

            productRepo.saveAll(products);
            return products.size();

        } catch (Exception e) {
            throw new RuntimeException("Грешка при CSV import: " + e.getMessage(), e);
        }
    }

    // ===== helpers =====

    private static String req(CSVRecord r, String col) {
        String v = r.get(col);
        if (v == null || v.isBlank()) {
            throw new IllegalArgumentException("Липсва задължителна колона: " + col);
        }
        return v.trim();
    }

    private static String opt(CSVRecord r, String col, String def) {
        try {
            String v = r.get(col);
            if (v == null || v.isBlank()) return def;
            return v.trim();
        } catch (IllegalArgumentException e) {
            return def;
        }
    }

    private static boolean parseBool(String s) {
        return "true".equalsIgnoreCase(s)
                || "1".equals(s)
                || "yes".equalsIgnoreCase(s);
    }

    private static float parseFloat(String s) {
        if (s == null || s.isBlank()) return 0f;
        return Float.parseFloat(s.replace(",", "."));
    }
}
