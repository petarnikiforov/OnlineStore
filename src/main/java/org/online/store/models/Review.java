//package org.online.store.models;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.UUID;
//
//@Data
//@Builder
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//public class Review {
//    @Id
//    @GeneratedValue
//    private UUID id;
//
//    private String comment;
//    private int rating;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Product product;
//}
