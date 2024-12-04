package com.beulah.bookmanagementweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public GenericResponse(String message, T data, MetaData metaData) {
        this.message = message;
        this.data = data;
        this.metaData = metaData;
    }

    public GenericResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }
}
