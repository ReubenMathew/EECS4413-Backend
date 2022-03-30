package com.shopcart.backend.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Column(name="date", nullable = false, updatable = false)
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
    @Column(name="product_ids")
    @ElementCollection
    private List<Long> product_ids = new ArrayList<Long>();
    
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

    public void addProduct_id(long product_id){
    	product_ids.add(product_id);
    }

    public void removeProduct_id(long product_id){
    	product_ids.remove(product_id);
    }

	public List<Long> getProduct_ids() {
		return product_ids;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && this.getClass() == object.getClass()) {
			Order other = (Order) object;
			return this.id == other.id && this.total == other.total && this.country.equals(other.country)
					&& this.first_name.equals(other.first_name) && this.last_name.equals(other.last_name)
					&& this.status.getValue() == other.status.getValue() && this.product_ids.containsAll(other.product_ids)
					&& other.product_ids.containsAll(this.product_ids);
		} else {
			return false;
		}
	}
}
