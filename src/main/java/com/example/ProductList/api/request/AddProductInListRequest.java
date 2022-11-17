package com.example.ProductList.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "add product to list request data model")
public class AddProductInListRequest {
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("list_id")
    private String listId;
}