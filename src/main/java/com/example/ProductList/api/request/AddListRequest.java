package com.example.ProductList.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "product list request data model")
public class AddListRequest {
    private String name;
}