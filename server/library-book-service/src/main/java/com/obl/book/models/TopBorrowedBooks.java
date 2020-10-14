package com.obl.book.models;

public class TopBorrowedBooks {

	Long borrowCount;
	Book borrowedBook;
	
	public TopBorrowedBooks(Long borrowCount, Book borrowedBook) {
		super();
		this.borrowCount = borrowCount;
		this.borrowedBook = borrowedBook;
	}
	public Long getBorrowCount() {
		return borrowCount;
	}
	public void setBorrowCount(Long borrowCount) {
		this.borrowCount = borrowCount;
	}
	public Book getBorrowedBook() {
		return borrowedBook;
	}
	public void setBorrowedBook(Book borrowedBook) {
		this.borrowedBook = borrowedBook;
	}
	@Override
	public String toString() {
		return "TopBorrowedBooks [borrowCount=" + borrowCount + ", borrowedBook=" + borrowedBook + "]";
	}
	
	
	
}
