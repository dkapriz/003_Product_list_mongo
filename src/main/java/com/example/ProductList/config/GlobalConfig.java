package com.example.ProductList.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {
    public static final String ERROR_MSG_WRONG_PARAMETER = "Wrong values passed to one or more parameters";
    public static final String ERROR_MSG_SHORT_NAME_PRODUCT = "Short product name";
    public static final String ERROR_MSG_LONG_NAME_PRODUCT = "Long product name";
    public static final String ERROR_MSG_SHORT_NAME_LIST = "Short product name";
    public static final String ERROR_MSG_LONG_NAME_LIST = "Long product name";
    public static final String ERROR_MSG_KCAL = "The kcal field is not set";
    public static final int MIN_PRODUCT_NAME_LENGTH = 5;
    public static final int MAX_PRODUCT_NAME_LENGTH = 50;
    public static final int MIN_LIST_NAME_LENGTH = 5;
    public static final int MAX_LIST_NAME_LENGTH = 30;
    public static final String SORT_PRODUCT_PARAMETER = "name";

}
