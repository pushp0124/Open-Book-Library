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
import com.obl.book.models.enums.IssueBookStatus;


@Entity
public class IssueBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer issueId;
	
	@ManyToOne
	private User issuedToUser;
	
	
	@Column(nullable = false)
	private Date issueDate;
	
	@Column(nullable = false)
	private Date returnDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private IssueBookStatus bookStatus; 
	
	@ManyToOne
	private Book issuedBook;

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public User getIssuedToUser() {
		return issuedToUser;
	}

	public void setIssuedToUser(User issuedToUser) {
		this.issuedToUser = issuedToUser;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Book getIssuedBook() {
		return issuedBook;
	}

	public void setIssuedBook(Book issuedBook) {
		this.issuedBook = issuedBook;
	}

	public IssueBookStatus getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(IssueBookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}

	@Override
	public String toString() {
		return "IssueBook [issueId=" + issueId + ", issuedToUser=" + issuedToUser + ", issueDate=" + issueDate
				+ ", returnDate=" + returnDate + ", bookStatus=" + bookStatus + ", issuedBook=" + issuedBook + "]";
	}

	
	
}
