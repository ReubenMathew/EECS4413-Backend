package com.shopcart.backend.repository;

import com.shopcart.backend.entity.Review;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
