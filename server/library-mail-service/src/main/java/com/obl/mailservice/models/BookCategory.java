package com.obl.mailservice.models;


public class BookCategory {

	private Integer categoryId;
	
	private String category;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "BookCategory [categoryId=" + categoryId + ", category=" + category + "]";
	}
	
	
}