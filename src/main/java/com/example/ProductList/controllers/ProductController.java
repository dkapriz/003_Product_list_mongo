package com.example.ProductList.controllers;

import com.example.ProductList.api.request.*;
import com.example.ProductList.api.response.ApiResponse;
import com.example.ProductList.api.response.ProductListResponse;
import com.example.ProductList.api.response.ResultResponse;
import com.example.ProductList.exception.UserInputParameterException;
import com.example.ProductList.exception.WrongParameterException;
import com.example.ProductList.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api("product REST controller")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add/product")
    public ResponseEntity<ApiResponse<ResultResponse>> addProduct(@RequestBody AddProductRequest addProductRequest)
            throws UserInputParameterException, WrongParameterException {
        return getResultResponseStatus200(productService.addProduct(addProductRequest));
    }

    @PostMapping("/add/list")
    public ResponseEntity<ApiResponse<ResultResponse>> addProductList(@RequestBody AddListRequest addListRequest)
            throws UserInputParameterException {
        return getResultResponseStatus200(productService.addProductList(addListRequest.getName()));
    }

    @PostMapping("/addProductToList")
    public ResponseEntity<ApiResponse<ResultResponse>> addProductToList(
            @RequestBody AddProductInListRequest addListRequest)
            throws WrongParameterException {
        return getResultResponseStatus200(productService.addProductToList(addListRequest));
    }

    @GetMapping("/getAllProduct")
    public ResponseEntity<ApiResponse<ProductListResponse>> getAllProduct(
            @RequestBody PageRequest pageRequest) throws WrongParameterException {
        return getProductListResponseStatus200(productService.getAllProducts(pageRequest.getOffset(),
                pageRequest.getLimit()));
    }

    @GetMapping("/getProductList")
    public ResponseEntity<ApiResponse<ProductListResponse>> getProductList(
            @RequestBody ProductListRequest productListRequest)
            throws WrongParameterException, MissingServletRequestParameterException {
        return getProductListResponseStatus200(productService.getProductFromList(productListRequest.getOffset(),
                productListRequest.getLimit(), productListRequest.getListId()));
    }

    private ResponseEntity<ApiResponse<ResultResponse>> getResultResponseStatus200(ResultResponse data) {
        ApiResponse<ResultResponse> response = new ApiResponse<>();
        response.setDebugMessage("successful request");
        response.setStatus(HttpStatus.OK);
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<ApiResponse<ProductListResponse>> getProductListResponseStatus200(ProductListResponse data) {
        ApiResponse<ProductListResponse> response = new ApiResponse<>();
        response.setDebugMessage("successful request");
        response.setStatus(HttpStatus.OK);
        response.setData(data);
        return ResponseEntity.ok(response);
    }
}
