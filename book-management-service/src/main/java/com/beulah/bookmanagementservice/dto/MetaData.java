package com.beulah.bookmanagementservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class MetaData {
    @ApiModelProperty(notes = "Current page number", example = "0")
    private int page;
    @ApiModelProperty(notes = "Number of elements per page", example = "20")
    private int perPage;
    @ApiModelProperty(notes = "Total number of pages", example = "5")
    private int totalPages;
    @ApiModelProperty(notes = "Total number of elements", example = "100")
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
