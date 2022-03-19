package com.shopcart.backend.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopcart.backend.entity.Order;
import com.shopcart.backend.entity.Product;
import com.shopcart.backend.repository.OrderRepository;
import com.shopcart.backend.repository.ProductRepository;
import com.shopcart.backend.service.DummyPaymentService;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    public DummyPaymentService dummyPaymentService = DummyPaymentService.getInstance();
    
    public OrderRestController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Order> getAllOrders(
    	@RequestParam("country") Optional<String> country,
    	@RequestParam("total") Optional<Float> total)
    {
    	Set<Order> orders = new HashSet<>();
    	orders.addAll((List<Order>) orderRepository.findAll());
    	if(country.isPresent()) orders.retainAll(orderRepository.findByCountry(country.get()));
    	if(total.isPresent()) {
    		for(Order order : orders) {
    			if(order.getTotal() > total.get()) orders.remove(order); 
    		}
    	}
        return new ArrayList<>(orders);
    }

    @GetMapping("/{order_id}")
    public Order getOrder(@PathVariable long order_id) {
    	return orderRepository.findById(order_id).orElse(null);
    }

    @PostMapping("/process")
    public String processOrder(@RequestBody Order order) {
    	for(Map.Entry<Long, Integer> entry : order.getProducts().entrySet()) {
    		Product product = productRepository.findById(entry.getKey()).orElse(null);
    		if(!(product != null && entry.getValue() <= product.getQuantity())) { // Too Many Items
            	return "{\"Order\":\"Items Added Exceeds Quantity In Stock\"}";	
    		}
    	}
		order.setStatus(1); // PROCESSING
		orderRepository.save(order);
    	return "{\"Order\":\"Sucessfully Submitted\", " 
				+ "\"id\":" + order.getId() + ", " 
				+ "\"status\":" + order.getStatus() + "}";	
    }

    @PutMapping("/submit")
    public String confirmOrder(@RequestBody Order order) {
    	if(!orderRepository.findById(order.getId()).orElse(null).equals(order)) return "{\"Order\":\"Order Not Found\"}";
    	if(order.getStatus() != 1) return "{\"Order\":\"Order Already Paid For\"}";	
    	if(dummyPaymentService.pay()) { // Successful Payment
    		for(Map.Entry<Long, Integer> entry : order.getProducts().entrySet()) {
        		Product product = productRepository.findById(entry.getKey()).orElse(null);
        		if(product != null) { // Deduct Quantity In Stock
        			product.setQuantity(product.getQuantity() - entry.getValue());
        			productRepository.save(product);
        		}
    		}
    		order.setStatus(2); // PAID
    		orderRepository.save(order);
        	return "{\"Order\":\"Order Successfully Completed\"}";
    	} else { // Unsuccessful Payment
        	return "{\"Order\":\"Credit Card Authorization Failed\"}";	
    	}
    }

    @DeleteMapping("/{order_id}")
    public void deleteOrder(@PathVariable long order_id) {
    	orderRepository.deleteById(order_id);
    }
}
