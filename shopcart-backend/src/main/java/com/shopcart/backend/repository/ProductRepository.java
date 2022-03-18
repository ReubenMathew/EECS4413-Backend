package com.shopcart.backend.repository;

import com.shopcart.backend.entity.Product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findByProductName(String productName);
	List<Product> findByBrand(String brand);
	List<Product> findByCategory(String category);
}
