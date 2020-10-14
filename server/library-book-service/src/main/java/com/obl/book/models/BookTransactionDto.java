package com.obl.book.models;

import java.util.List;

public class BookTransactionDto {
	
	private Long total_count;
	private List<BookTransaction> books;
	public Long getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Long total_count) {
		this.total_count = total_count;
	}
	
	public List<BookTransaction> getBooks() {
		return books;
	}
	public void setBooks(List<BookTransaction> books) {
		this.books = books;
	}
	@Override
	public String toString() {
		return "IssueBookApi [total_count=" + total_count + ", books=" + books + "]";
	}
	
	
	
	

}
