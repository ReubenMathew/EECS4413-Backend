package com.shopcart.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="products")
public class Product {

	private enum Size {
		XXS(1), XS(2), S(3), M(4), L(5), XL(6), XXL(7);
		private int value;
		Size(int value) { this.value = value; }
		public int getValue() { return value; }
	}

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
    @Column(name="size")
    @Enumerated(EnumType.STRING)
    private Size size;
    @Column(name="image_url")
    private String image_url;

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

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
}
