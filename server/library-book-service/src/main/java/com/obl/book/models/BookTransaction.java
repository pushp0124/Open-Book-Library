package com.obl.book.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.obl.book.models.common.User;
import com.obl.book.models.enums.BookTransactionStatus;


@Entity
public class BookTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;
	
	@ManyToOne
	private User borrowedToUser;
	
	
	@Column(nullable = false)
	private Date borrowedDate;
	
	@Column(nullable = false)
	private Date returnDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BookTransactionStatus bookStatus; 
	
	@ManyToOne
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

	@Override
	public String toString() {
		return "IssueBook [transactionId=" + transactionId + ", borrowedToUser=" + borrowedToUser + ", borrowedDate=" + borrowedDate
				+ ", returnDate=" + returnDate + ", bookStatus=" + bookStatus + ", borrowedBook=" + borrowedBook + "]";
	}

	
	
}
