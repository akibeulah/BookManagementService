package com.beulah.bookmanagementservice.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class MetaData {
    private int page;
    private int perPage;
    private int totalPages;
    private int totalElements;

    public MetaData(int page, int perPage, int totalElements, int totalPages) {
        this.page = page;
        this.perPage = perPage;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public MetaData(Page<?> pageData) {
        this.page = pageData.getPageable().getPageNumber();
        this.perPage = pageData.getPageable().getPageSize();
        this.totalElements = (int) pageData.getTotalElements();
        this.totalPages = pageData.getTotalPages();
    }
}
