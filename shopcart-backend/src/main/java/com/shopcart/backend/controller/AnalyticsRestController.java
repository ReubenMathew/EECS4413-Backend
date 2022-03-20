package com.shopcart.backend.controller;

import java.util.Calendar;
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

	@GetMapping("report/monthly/items")
    public String getMonthlyItemsSold() {
    	int[] quantities_sold = new int[12];
    	for(Order order : orderRepository.findAll()) {
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(order.getDate());
    		int month = cal.get(Calendar.MONTH);
        	for(Map.Entry<Long, Integer> entry : order.getProducts().entrySet()) {
        		quantities_sold[month] += entry.getValue();
        	}
    	}

    	// Return Monthly Items Sold
    	StringBuilder result = new StringBuilder();
    	result.append("{");
    	for(int i = 0; i < months.length; i ++) {
    		result.append("\"" + months[i] + "\":");
    		result.append(quantities_sold[i]);
			if(i < months.length - 1) result.append(", ");
    	}
    	result.append("}");
    	return result.toString();
    }

	@GetMapping("report/website/usage")
    public String getWebsiteUsage() {
		int view_hits = visitEventRepository.findByEvent(Event.VIEW).size();
		int cart_hits = visitEventRepository.findByEvent(Event.CART).size();
		int purchase_hits = visitEventRepository.findByEvent(Event.PURCHASE).size();

    	// Return Website Usage
    	StringBuilder result = new StringBuilder();
    	result.append("{\"view\":");
    	result.append(view_hits);
    	result.append(", \"cart\":");
    	result.append(cart_hits);
    	result.append(", \"purchase\":");
    	result.append(purchase_hits + "}");
    	return result.toString();
	}

	@PostMapping("report/website/usage")
    public void addPageVisit(@RequestBody VisitEvent visitEvent) {
		visitEventRepository.save(visitEvent);
	}
	
	@DeleteMapping("report/website/usage")
    public void deletePageVisit(@RequestBody VisitEvent visitEvent) {
		visitEventRepository.delete(visitEvent);
	}
}
