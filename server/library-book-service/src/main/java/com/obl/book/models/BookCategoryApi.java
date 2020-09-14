package com.obl.book.models;

import java.util.List;

public class BookCategoryApi {
	private Long total_count;
	private List<BookCategory> categories;
	public Long getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Long total_count) {
		this.total_count = total_count;
	}
	
	public List<BookCategory> getCategories() {
		return categories;
	}
	public void setCategories(List<BookCategory> categories) {
		this.categories = categories;
	}
	@Override
	public String toString() {
		return "BookCategoryApi [total_count=" + total_count + ", categories=" + categories + "]";
	}
	
}
