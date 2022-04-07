package com.shopcart.backend.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopcart.backend.entity.Order;
import com.shopcart.backend.entity.VisitEvent;
import com.shopcart.backend.entity.VisitEvent.Event;
import com.shopcart.backend.repository.OrderRepository;
import com.shopcart.backend.repository.VisitEventRepository;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsRestController {

	private final String[] months = {
			"January",
			"February",
			"March",
			"April",
			"May",
			"June",
			"July",
			"August",
			"September",
			"October",
			"November",
			"December"
	};

    private final OrderRepository orderRepository;
    private final VisitEventRepository visitEventRepository;

    public AnalyticsRestController(OrderRepository orderRepository, VisitEventRepository visitEventRepository) {
        this.orderRepository = orderRepository;
        this.visitEventRepository = visitEventRepository;
    }

	@GetMapping("/monthly/items")
    public Map getMonthlyItemsSold() {
        HashMap<String, Integer> responseMap = new HashMap<>();
    	int[] quantities_sold = new int[12];
    	for(Order order : orderRepository.findAll()) {
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(order.getDate());
    		int month = cal.get(Calendar.MONTH);
    		quantities_sold[month] += order.getProduct_ids().size();
    	}

    	// Return Monthly Items Sold
    	for(int i = 0; i < months.length; i ++) {
    		responseMap.put(months[i], quantities_sold[i]);
    	}
    	return responseMap;
    }

	@GetMapping("/website/usage")
    public Map getWebsiteUsage() {
        HashMap<String, Integer> responseMap = new HashMap<>();
		int view_hits = visitEventRepository.findByEvent(Event.VIEW).size();
		int cart_hits = visitEventRepository.findByEvent(Event.CART).size();
		int purchase_hits = visitEventRepository.findByEvent(Event.PURCHASE).size();

    	// Return Website Usage
    	responseMap.put("view", view_hits);
    	responseMap.put("cart", cart_hits);
    	responseMap.put("purchase", purchase_hits);
    	return responseMap;
	}

	@PostMapping("/website/usage")
    public void addPageVisit(@RequestBody VisitEvent visitEvent) {
		visitEventRepository.save(visitEvent);
	}
	
	@DeleteMapping("/website/usage")
    public void deletePageVisit(@RequestBody VisitEvent visitEvent) {
		visitEventRepository.delete(visitEvent);
	}
}
