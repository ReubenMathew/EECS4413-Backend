package com.shopcart.catalogservice.repository;

import com.shopcart.catalogservice.entity.Review;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
