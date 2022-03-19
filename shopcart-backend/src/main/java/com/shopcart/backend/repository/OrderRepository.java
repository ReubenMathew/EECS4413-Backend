package com.shopcart.backend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopcart.backend.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	public List<Order> findByCountry(String country);
}
