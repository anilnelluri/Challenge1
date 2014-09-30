package com.umkc.grocerymart;

public class ProductInfo {

	private String name;
	private String description;
	private String category;
	private String id;
	private double price;

	ProductInfo(String name, String description, String category, String id, double price) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.id = id;
		this.price = price;

	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public String getId() {
		return id;
	}

	public double getPrice() {
		return price;
	}

}
