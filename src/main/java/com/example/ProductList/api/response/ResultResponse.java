package com.example.ProductList.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "data model of result response")
public class ResultResponse {
    protected Boolean result;
    @JsonProperty("error")
    protected String errorMessage;

    public ResultResponse(boolean result) {
        this.result = result;
        errorMessage = null;
    }

    public ResultResponse(String errorMessage) {
        result = false;
        this.errorMessage = errorMessage;
    }
}
