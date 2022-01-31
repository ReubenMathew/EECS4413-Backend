package com.shopcart.catalogservice.repository;

import com.shopcart.catalogservice.entity.Product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	public List<Product> findByProductName(String productName);
	public List<Product> findByBrand(String brand);
	public List<Product> findByCategory(String category);
}
