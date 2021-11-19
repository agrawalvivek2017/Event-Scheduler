package com.events.scheduler.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    T body;
    int code;
    String errorReason = null;

    private Response() {}

    public Response(T body, int code) {
        this.body = body;
        this.code = code;
    }

    public Response(int code, String errorReason) {
        this.code = code;
        this.errorReason = errorReason;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}
