package com.shopcart.catalogservice.controller;

import com.shopcart.catalogservice.entity.Product;
import com.shopcart.catalogservice.entity.Review;
import com.shopcart.catalogservice.repository.ProductRepository;
import com.shopcart.catalogservice.repository.ReviewRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {
	
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewRestController(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }
    
    @GetMapping("/product/{product_id}")
    public List<Review> getReviewsForProduct(@PathVariable long product_id) {
        Product product = productRepository.findById(product_id).orElse(null);
        List<Long> review_ids = product.getReview_ids();
        return (List<Review>) reviewRepository.findAllById(review_ids);
    }

    @GetMapping("/{review_id}")
    public Review getReview(@PathVariable long review_id) {
    	return reviewRepository.findById(review_id).orElse(null);
    }

    @PostMapping("/{product_id}")
    public void addReview(@RequestBody Review review, @PathVariable long product_id) {
    	reviewRepository.save(review);
        Product product = productRepository.findById(product_id).orElse(null);
        product.addReview_id(review.getId());
        productRepository.save(product);
    }

    @PutMapping("/{review_id}")
    public void updateReview(@RequestBody Review review, @PathVariable long review_id) {
    	if(review_id == review.getId()) reviewRepository.save(review);
    }

    @DeleteMapping("/{review_id}")
    public void deleteReview(@RequestBody Review review, @PathVariable long review_id) {
    	reviewRepository.delete(review);
        Product product = productRepository.findById(review_id).orElse(null);
        product.removeReview_id(review.getId());
        productRepository.save(product);
    }
}
