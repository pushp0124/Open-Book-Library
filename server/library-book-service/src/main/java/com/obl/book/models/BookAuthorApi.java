package com.obl.book.models;

import java.util.List;

public class BookAuthorApi {
	private Long total_count;
	private List<BookAuthor> authors;
	public Long getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Long total_count) {
		this.total_count = total_count;
	}
	
	public List<BookAuthor> getAuthors() {
		return authors;
	}
	public void setAuthors(List<BookAuthor> authors) {
		this.authors = authors;
	}
	@Override
	public String toString() {
		return "BookAuthorApi [total_count=" + total_count + ", authors=" + authors + "]";
	}
	
	
	
}
