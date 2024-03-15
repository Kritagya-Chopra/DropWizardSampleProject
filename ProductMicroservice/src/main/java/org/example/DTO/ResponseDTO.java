package org.example.DTO;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDTO<T> {
    private long code;

    @Length(max = 3)
    private T data;

    public ResponseDTO() {
        // Jackson deserialization
    }

    public ResponseDTO(long code, T data) {
        this.code = code;
        this.data = data;
    }

    @JsonProperty
    public long getCode() {
        return code;
    }

    @JsonProperty
    public T getData() {
        return data;
    }
}