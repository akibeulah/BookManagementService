package com.beulah.bookmanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class GenericResponse<T> {
    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    @JsonProperty("errors")
    private List<String> errors;

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
