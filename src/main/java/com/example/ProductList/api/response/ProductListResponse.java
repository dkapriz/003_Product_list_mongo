package com.example.ProductList.api.response;

import com.example.ProductList.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "data model of product response")
public class ProductListResponse {
    private Long count;

    @JsonProperty("kcal_amount")
    private Integer kcalAmount;
    private List<Product> products;
}
