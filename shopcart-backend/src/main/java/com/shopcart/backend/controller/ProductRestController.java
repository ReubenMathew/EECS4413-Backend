package com.shopcart.backend.controller;

import com.shopcart.backend.entity.Product;
import com.shopcart.backend.repository.ProductRepository;
import com.shopcart.backend.repository.ReviewRepository;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ProductRestController(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public List<Product> getAllProducts(
    	@RequestParam("name") Optional<String> name,
    	@RequestParam("brand") Optional<String> brand,
    	@RequestParam("category") Optional<String> category)
    {
    	Set<Product> products = new HashSet<>();
    	products.addAll((List<Product>) productRepository.findAll());
    	if(name.isPresent()) products.retainAll(productRepository.findByProductName(name.get()));
    	if(brand.isPresent()) products.retainAll(productRepository.findByBrand(brand.get()));
    	if(category.isPresent()) products.retainAll(productRepository.findByCategory(category.get()));
        return new ArrayList<>(products);
    }

    @GetMapping("/{product_id}")
    public Product getProduct(@PathVariable long product_id) {
    	return productRepository.findById(product_id).orElse(null);
    }

    @PostMapping
    public void addProduct(@RequestBody Product product) {
        productRepository.save(product);
    }
    
    @PutMapping("/{product_id}")
    public void updateProduct(@PathVariable long product_id, @RequestBody Product product) {
    	if(product_id == product.getId()) productRepository.save(product);
    }

    @DeleteMapping("/{product_id}")
    public void deleteProduct(@PathVariable long product_id) {
    	Product product = productRepository.findById(product_id).orElse(null);
    	reviewRepository.deleteAllById(product.getReview_ids());
    	productRepository.deleteById(product.getId());
    }
}
