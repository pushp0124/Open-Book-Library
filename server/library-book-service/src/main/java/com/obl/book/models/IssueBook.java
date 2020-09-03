package com.obl.book.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.obl.book.models.common.User;


@Entity
public class IssueBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer issueId;
	
	@ManyToOne
	private User issuedToUser;
	
	
	private Date issueDate;
	
	private Date returnDate;
	
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

	@Override
	public String toString() {
		return "IssueBook [issueId=" + issueId + ", issuedToUser=" + issuedToUser + ", issueDate=" + issueDate
				+ ", returnDate=" + returnDate + ", issuedBook=" + issuedBook + "]";
	}


	
	
	
	
	
}
