package com.benit.backend_codecademy_news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude (JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {
    private Date timestamp;
    private String message;
    private boolean error=false;
    private T data=null;

    public CustomResponse(Date timestamp, String message, T data) {
        this.timestamp = timestamp;
        this.message = message;
        this.data = data;
    }
}
