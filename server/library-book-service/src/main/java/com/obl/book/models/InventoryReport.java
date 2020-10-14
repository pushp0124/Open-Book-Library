package com.obl.book.models;

public class InventoryReport {
	
	Integer totalBooks; // available + unavailable (lost + borrowed)
	Integer totalMembers; // all including admins
	Long totalPriceOfBooks; // sum of all books price
	Integer totalBooksLost; // lost books count
	Integer borrowedBooks; // total = available + lost + borrowed
	public Integer getTotalBooks() {
		return totalBooks;
	}
	public void setTotalBooks(Integer totalBooks) {
		this.totalBooks = totalBooks;
	}
	public Integer getTotalMembers() {
		return totalMembers;
	}
	public void setTotalMembers(Integer totalMembers) {
		this.totalMembers = totalMembers;
	}
	public Long getTotalPriceOfBooks() {
		return totalPriceOfBooks;
	}
	public void setTotalPriceOfBooks(Long totalPriceOfBooks) {
		this.totalPriceOfBooks = totalPriceOfBooks;
	}
	public Integer getTotalBooksLost() {
		return totalBooksLost;
	}
	public void setTotalBooksLost(Integer totalBooksLost) {
		this.totalBooksLost = totalBooksLost;
	}
	public Integer getBorrowedBooks() {
		return borrowedBooks;
	}
	public void setBorrowedBooks(Integer borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}
	@Override
	public String toString() {
		return "InventoryReport [totalBooks=" + totalBooks + ", totalMembers=" + totalMembers + ", totalPriceOfBooks="
				+ totalPriceOfBooks + ", totalBooksLost=" + totalBooksLost + ", borrowedBooks=" + borrowedBooks + "]";
	}
	
	

}
