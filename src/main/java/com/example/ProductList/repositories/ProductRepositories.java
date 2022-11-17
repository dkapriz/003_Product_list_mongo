package com.example.ProductList.repositories;

import com.example.ProductList.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositories extends MongoRepository<Product, String> {
}
