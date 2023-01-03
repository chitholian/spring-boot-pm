package com.cnsbd.jtrainpm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonResponse {
    @JsonProperty
    public Integer status = 200;

    @JsonProperty
    public Object data;

    public JsonResponse(Integer status, Object data) {
        this.status = status;
        this.data = data;
    }

    public JsonResponse(Object data) {
        this.data = data;
    }
}
