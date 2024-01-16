package com.example.errorresponse.exception;

import com.example.errorresponse.entity.ErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.AbstractMap;
import java.util.Map;

public class CustomException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;
    private String message;

    @Getter
    private Map.Entry<String, Object> data;


    @Override
    public String getMessage() {
//        if (StringUtils.hasLength(errorCode.getMessage())) {
        if (StringUtils.hasLength(this.message)) {
            return this.message;
        }
        return errorCode.getMessage();

    }

    public CustomException(ErrorCode errorCode, String message, Object data) {
        this.errorCode = errorCode;
        this.message = message;

        // 하위과제 null 처리
        this.data = data != null ? new AbstractMap.SimpleEntry<>(data.getClass().getSimpleName(), data) : null;
    }



}
