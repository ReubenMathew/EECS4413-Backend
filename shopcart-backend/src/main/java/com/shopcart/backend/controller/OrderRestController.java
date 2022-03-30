package com.shopcart.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
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
    public Map processOrder(@RequestBody Order order) {
        HashMap<String, String> responseMap = new HashMap<>();
        if(order.getProduct_ids().size() == 0) {
			responseMap.put("Order", "No Items In Cart");
			return responseMap;
        }
    	for(Long id : order.getProduct_ids()) {
    		Product product = productRepository.findById(id).orElse(null);
    		if(product == null) {
    			responseMap.put("Order", "An Item In The Cart Dosent Exist");
    			return responseMap;
    		}
    	}
		order.setStatus(1); // PROCESSING
		orderRepository.save(order);
		responseMap.put("Order", "Sucessfully Submitted");
		responseMap.put("id", "" + order.getId());
		responseMap.put("status", "" + order.getStatus());
		return responseMap;
    }

    @PutMapping("/submit")
    public Map confirmOrder(@RequestBody Order order) {
        HashMap<String, String> responseMap = new HashMap<>();
        Order foundOrder = orderRepository.findById(order.getId()).orElse(null);
        if(foundOrder == null || !foundOrder.equals(order)) {
    		responseMap.put("Order", "Order Not Found");
    		return responseMap;
    	} else if(order.getStatus() != 1) {
    		responseMap.put("Order", "Order Already Paid For");
    		return responseMap;
    	}
    	if(dummyPaymentService.pay()) { // Successful Payment
    		order.setStatus(2); // PAID
    		orderRepository.save(order);
    		responseMap.put("Order", "Order Successfully Completed");
    		return responseMap;
    	} else { // Unsuccessful Payment
    		responseMap.put("Order", "Credit Card Authorization Failed");
    		return responseMap;    			
    	}
    }

    @DeleteMapping("/{order_id}")
    public void deleteOrder(@PathVariable long order_id) {
    	orderRepository.deleteById(order_id);
    }
}
