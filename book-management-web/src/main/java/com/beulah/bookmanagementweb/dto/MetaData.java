package com.beulah.bookmanagementweb.dto;

import lombok.Data;

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
}

