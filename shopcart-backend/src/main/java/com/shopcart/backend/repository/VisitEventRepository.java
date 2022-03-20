package com.shopcart.backend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopcart.backend.entity.VisitEvent;
import com.shopcart.backend.entity.VisitEvent.Event;

@Repository
public interface VisitEventRepository extends CrudRepository<VisitEvent, Long> {
	List<VisitEvent> findByEvent(Event event);
}