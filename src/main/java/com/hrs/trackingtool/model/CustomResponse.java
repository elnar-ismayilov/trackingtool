package com.hrs.trackingtool.model;

import com.hrs.trackingtool.modelvalue.CustomHttpStatus;
// Custom response model to some requests
public class CustomResponse {
    private String code;
    private String message;

    public CustomResponse() {}
    public CustomResponse(CustomHttpStatus customHttpStatus) {
        this.code = customHttpStatus.getValue();
        this.message = customHttpStatus.getReasonPhrase();
    }

    public CustomResponse(String code, String message,long id) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomResponse response = (CustomResponse) o;

        return code.equals(response.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "CustomResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
