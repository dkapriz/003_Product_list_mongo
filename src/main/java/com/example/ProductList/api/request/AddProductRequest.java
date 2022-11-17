package com.example.ProductList.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "add product request data model")
public class AddProductRequest {
    private String name;
    private String descriptions;
    private Integer kcal;
}
