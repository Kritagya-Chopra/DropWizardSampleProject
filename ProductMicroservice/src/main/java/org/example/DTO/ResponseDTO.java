package org.example.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDTO<T> {
    private long code;
    private T data;

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