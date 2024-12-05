package com.beulah.bookmanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class GenericResponse<T> {
    @ApiModelProperty(
            notes = "Response message indicating the result of the operation",
            example = "Book created successfully",
            position = 1
    )
    @JsonProperty("message")
    private String message;

    @ApiModelProperty(
            notes = "Response payload data",
            position = 2
    )
    @JsonProperty("data")
    private T data;

    @ApiModelProperty(
            notes = "List of error messages if any errors occurred",
            example = "[\"Invalid ISBN format\"]",
            position = 3
    )
    @JsonProperty("errors")
    private List<String> errors;

    @ApiModelProperty(
            notes = "Pagination metadata for list operations",
            position = 4
    )
    @JsonProperty("metadata")
    private MetaData metaData;

    public GenericResponse(String message) {
        this.message = message;
    }

    public GenericResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public GenericResponse(String message, T data, Page<?> metaData) {
        this.message = message;
        this.data = data;
        this.metaData = new MetaData(metaData);
    }

    public GenericResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }
}
