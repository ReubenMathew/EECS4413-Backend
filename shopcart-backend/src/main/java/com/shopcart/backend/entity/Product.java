package com.shopcart.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="product_name")
    private @NotBlank String productName;
    @Column(name="category")
    private @NotBlank String category;
    @Column(name="brand")
    private String brand;
    @Column(name="description")
    private String description;
    @Column(name="color")
    private String color;
    @Column(name="price")
    private @NotBlank float price;
    @Column(name="quantity")
    private @NotBlank int quantity;
    @Column(name="review_ids")
    @ElementCollection
    private List<Long> review_ids = new ArrayList<Long>();
    // TODO(Reuben): Include column for size, the implementation details of how this field will be stored and indexed is TBD
    // TODO(Reuben): Include column for product image, the implementation details of how this image will be store is TBD


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addReview_id(long review_id){
    	review_ids.add(review_id);
    }

    public void removeReview_id(long review_id){
    	review_ids.remove(review_id);
    }

	public List<Long> getReview_ids() {
		return review_ids;
	}
}
