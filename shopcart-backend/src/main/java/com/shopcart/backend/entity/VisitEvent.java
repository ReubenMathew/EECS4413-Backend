package com.shopcart.backend.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="visit_event")
public class VisitEvent {

	public enum Event {
		VIEW(1), CART(2), PURCHASE(3);
		private int value;
		Event(int value) { this.value = value; }
		public int getValue() { return value; }
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date date;
    @Column(name="ip_address")
    private String ip_address;
    @Column(name="event")
    @Enumerated(EnumType.STRING)
    private Event event;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(int event) {
		if(event == 1) {
			this.event = Event.VIEW;
		} else if(event == 2) {
			this.event = Event.CART;
		} else if(event == 3) {
			this.event = Event.PURCHASE;
		}
	}
}
