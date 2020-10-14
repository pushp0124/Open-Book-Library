package com.obl.book.models;

import java.util.List;

public class BookDto {

	private Long total_count;
	private List<Book> books;
	public Long getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Long total_count) {
		this.total_count = total_count;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	@Override
	public String toString() {
		return "BookApi [total_count=" + total_count + ", books=" + books + "]";
	}
	
	
}
