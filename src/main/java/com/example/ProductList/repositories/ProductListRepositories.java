package com.example.ProductList.repositories;

import com.example.ProductList.model.ProductList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductListRepositories extends MongoRepository<ProductList, String> {
}
