package com.obl.mailservice.models;

import java.sql.Date;
import com.obl.mailservice.enums.BookTransactionStatus;


public class BookTransaction {

	
	private Integer transactionId;
	
	
	private User borrowedToUser;
	
	
	private Date borrowedDate;
	
	private Date returnDate;
	
	private Integer fineTillDate;
	
	private BookTransactionStatus bookStatus; 
	
	
	private Book borrowedBook;

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public User getBorrowedToUser() {
		return borrowedToUser;
	}

	public void setBorrowedToUser(User borrowedToUser) {
		this.borrowedToUser = borrowedToUser;
	}

	public Date getBorrowedDate() {
		return borrowedDate;
	}

	public void setBorrowedDate(Date borrowedDate) {
		this.borrowedDate = borrowedDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Book getBorrowedBook() {
		return borrowedBook;
	}

	public void setBorrowedBook(Book borrowedBook) {
		this.borrowedBook = borrowedBook;
	}

	public BookTransactionStatus getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(BookTransactionStatus bookStatus) {
		this.bookStatus = bookStatus;
	}

	public Integer getFineTillDate() {
		return fineTillDate;
	}

	public void setFineTillDate(Integer fineTillDate) {
		this.fineTillDate = fineTillDate;
	}

	@Override
	public String toString() {
		return "BookTransaction [transactionId=" + transactionId + ", borrowedToUser=" + borrowedToUser
				+ ", borrowedDate=" + borrowedDate + ", returnDate=" + returnDate + ", fineTillDate=" + fineTillDate
				+ ", bookStatus=" + bookStatus + ", borrowedBook=" + borrowedBook + "]";
	}

	

	
	
}
