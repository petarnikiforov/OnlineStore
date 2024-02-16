package org.online.store.pagination;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.util.List;


@Data
@EqualsAndHashCode
public class CustomPage<T> {
    private List<T> content;
    private PaginationMetaData pagination;

    public CustomPage(Page<T> springPage) {
        this.content = springPage.getContent();
        this.pagination = PaginationMetaData.builder()
                .currentPage(springPage.getPageable().getPageNumber())
                .pageSize(springPage.getPageable().getPageSize())
                .totalElements(springPage.getTotalElements())
                .totalPages(springPage.getTotalPages())
                .build();
    }
}
