package com.example.ProductList.service;

import com.example.ProductList.api.request.AddProductInListRequest;
import com.example.ProductList.api.request.AddProductRequest;
import com.example.ProductList.api.response.ProductListResponse;
import com.example.ProductList.api.response.ResultResponse;
import com.example.ProductList.config.GlobalConfig;
import com.example.ProductList.exception.UserInputParameterException;
import com.example.ProductList.exception.WrongParameterException;
import com.example.ProductList.model.Product;
import com.example.ProductList.model.ProductList;
import com.example.ProductList.repositories.ProductListRepositories;
import com.example.ProductList.repositories.ProductRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProductService {
    private final ProductRepositories productRepositories;
    private final ProductListRepositories productListRepositories;

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    public ProductService(ProductRepositories productRepositories, ProductListRepositories productListRepositories) {
        this.productRepositories = productRepositories;
        this.productListRepositories = productListRepositories;
    }

    public ResultResponse addProduct(AddProductRequest addProductRequest) throws
            UserInputParameterException, WrongParameterException {
        checkProductData(addProductRequest);
        productRepositories.save(getProductFromRequest(addProductRequest));
        logger.info("add product " + addProductRequest.getName());
        return new ResultResponse(true);
    }

    public ResultResponse addProductList(String name) throws UserInputParameterException {
        checkProductListName(name);
        ProductList productList = new ProductList();
        productList.setName(name);
        productList = productListRepositories.save(productList);
        logger.info("add product list " + name + " " + productList.getId());
        return new ResultResponse(true);
    }

    public ResultResponse addProductToList(AddProductInListRequest addListRequest) throws WrongParameterException {
        checkId(addListRequest.getProductId());
        checkId(addListRequest.getListId());
        Optional<Product> product = productRepositories.findById(addListRequest.getProductId());
        Optional<ProductList> productList = productListRepositories.findById(addListRequest.getListId());
        if (product.isEmpty() || productList.isEmpty()) {
            throw new WrongParameterException(GlobalConfig.ERROR_MSG_WRONG_PARAMETER);
        }
        productList.get().addProduct(product.get());
        productListRepositories.save(productList.get());
        logger.info("add product " + product.get().getName() + " in product list " + productList.get().getName());
        return new ResultResponse(true);
    }

    public ProductListResponse getAllProducts(Integer offset, Integer limit) throws WrongParameterException {
        checkRequestPagination(offset, limit);
        Pageable pageable = PageRequest.of(offset, limit,
                Sort.by(GlobalConfig.SORT_PRODUCT_PARAMETER).ascending());
        Page<Product> products = productRepositories.findAll(pageable);

        ProductListResponse productList = new ProductListResponse();
        productList.setProducts(products.getContent());
        productList.setCount(products.getTotalElements());
        return productList;
    }

    public ProductListResponse getProductFromList(Integer offset, Integer limit, String listId)
            throws WrongParameterException, MissingServletRequestParameterException {
        checkRequestPagination(offset, limit);
        checkId(listId);
        Pageable pageable = PageRequest.of(offset, limit,
                Sort.by(GlobalConfig.SORT_PRODUCT_PARAMETER).ascending());
        Optional<ProductList> productList = productListRepositories.findById(listId);
        if (productList.isEmpty()) {
            throw new WrongParameterException(GlobalConfig.ERROR_MSG_WRONG_PARAMETER);
        }
        Page<Product> products = new PageImpl<>(productList.get().getProducts(), pageable,
                productList.get().getProducts().size());

        ProductListResponse productListResponse = new ProductListResponse();
        productListResponse.setProducts(products.getContent());
        productListResponse.setCount(products.getTotalElements());
        productListResponse.setKcalAmount(getAmountKcal(productList.get().getProducts()));
        return productListResponse;
    }

    private Integer getAmountKcal(List<Product> productList) throws MissingServletRequestParameterException {
        Optional<Integer> amount = productList.stream().map(Product::getKcal).reduce(Integer::sum);
        if (amount.isEmpty()) {
            throw new MissingServletRequestParameterException("getAmountKcal - amount", "Integer");
        }
        return amount.get();
    }

    private Product getProductFromRequest(AddProductRequest addProductRequest) {
        Product product = new Product();
        product.setName(addProductRequest.getName());
        product.setDescriptions(addProductRequest.getDescriptions());
        product.setKcal(addProductRequest.getKcal());
        return product;
    }

    private void checkProductData(AddProductRequest addProductRequest) throws
            UserInputParameterException, WrongParameterException {
        if (addProductRequest.getName().length() < GlobalConfig.MIN_PRODUCT_NAME_LENGTH) {
            throw new UserInputParameterException(GlobalConfig.ERROR_MSG_SHORT_NAME_PRODUCT);
        }
        if (addProductRequest.getName().length() > GlobalConfig.MAX_PRODUCT_NAME_LENGTH) {
            throw new UserInputParameterException(GlobalConfig.ERROR_MSG_LONG_NAME_PRODUCT);
        }
        if (addProductRequest.getKcal() == 0) {
            throw new WrongParameterException(GlobalConfig.ERROR_MSG_KCAL);
        }
    }

    private void checkProductListName(String name) throws UserInputParameterException {
        if (name.length() < GlobalConfig.MIN_LIST_NAME_LENGTH) {
            throw new UserInputParameterException(GlobalConfig.ERROR_MSG_SHORT_NAME_LIST);
        }
        if (name.length() > GlobalConfig.MAX_LIST_NAME_LENGTH) {
            throw new UserInputParameterException(GlobalConfig.ERROR_MSG_LONG_NAME_LIST);
        }
    }

    private void checkId(String id) throws WrongParameterException {
        if (id.isEmpty()) {
            throw new WrongParameterException(GlobalConfig.ERROR_MSG_WRONG_PARAMETER);
        }
    }

    private void checkRequestPagination(Integer offset, Integer limit) throws WrongParameterException {
        checkIntegerParameter(offset);
        checkIntegerParameter(limit);
    }

    private void checkIntegerParameter(Integer paramInt) throws WrongParameterException {
        checkLongParameter(paramInt.longValue());
    }

    private void checkLongParameter(Long paramLong) throws WrongParameterException {
        if (paramLong == null) {
            throw new WrongParameterException(GlobalConfig.ERROR_MSG_WRONG_PARAMETER);
        }
        if (paramLong < 0) {
            throw new WrongParameterException(GlobalConfig.ERROR_MSG_WRONG_PARAMETER);
        }
    }
}
