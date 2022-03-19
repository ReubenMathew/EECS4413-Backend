package com.shopcart.backend.entity;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="orders")
public class Order {

	private enum Status {
		PROCESSING(1), PAID(2);
		private int value;
		Status(int value) { this.value = value; }
		public int getValue() { return value; }
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date date;
    @Column(name="total")
    private @NotBlank float total;
    @Column(name="first_name")
    private @NotBlank String first_name;
    @Column(name="last_name")
    private String last_name;
    @Column(name="country")
    private String country;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @ElementCollection
    @CollectionTable(name = "order_products")
    @MapKeyColumn(name="product_id")
    @Column(name="quantity")
    Map<Long, Integer> products = new HashMap<Long, Integer>();
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setTimestamp(Date date) {
		this.date = date;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Status getStatusVal() {
		return status;
	}

	public int getStatus() {
		return status.getValue();
	}
	
	public void setStatus(int status) {
		if(status == 1) {
			this.status = Status.PROCESSING;
		} else if(status == 2) {
			this.status = Status.PAID;
		}
	}

	public void addProduct(long product_id, int quantity){
		products.put(product_id, quantity);
	}

	public void removeProduct(long product_id){
		products.remove(product_id);
	}

	public Map<Long, Integer> getProducts() {
		return products;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && this.getClass() == object.getClass()) {
			Order other = (Order) object;
			return this.id == other.id && this.total == other.total && this.country.equals(other.country)
					&& this.first_name.equals(other.first_name) && this.last_name.equals(other.last_name)
					&& this.products.equals(other.products) && this.status.getValue() == other.status.getValue();
		} else {
			return false;
		}
	}
}
