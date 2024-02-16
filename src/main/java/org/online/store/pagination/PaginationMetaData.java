package org.online.store.pagination;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationMetaData {
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
}
