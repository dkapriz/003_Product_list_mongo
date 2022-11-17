package com.example.ProductList.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(description = "product list request data model")
public class ProductListRequest extends PageRequest {
    @JsonProperty("list_id")
    private String listId;
}
