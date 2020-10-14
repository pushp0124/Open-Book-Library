package com.obl.gateway_security.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LibraryConstants {

	@Id
	private Integer constantId;
	
	private Integer lateDepositFinePerDay;
	
	private Integer maximumBooksLimit;
	
	private Integer bookBorrowDays;

	public Integer getConstantId() {
		return constantId;
	}

	public void setConstantId(Integer constantId) {
		this.constantId = constantId;
	}

	public Integer getLateDepositFinePerDay() {
		return lateDepositFinePerDay;
	}

	public void setLateDepositFinePerDay(Integer lateDepositFinePerDay) {
		this.lateDepositFinePerDay = lateDepositFinePerDay;
	}

	public Integer getMaximumBooksLimit() {
		return maximumBooksLimit;
	}

	public void setMaximumBooksLimit(Integer maximumBooksLimit) {
		this.maximumBooksLimit = maximumBooksLimit;
	}

	public Integer getBookBorrowDays() {
		return bookBorrowDays;
	}

	public void setBookBorrowDays(Integer bookBorrowDays) {
		this.bookBorrowDays = bookBorrowDays;
	}

	@Override
	public String toString() {
		return "LibraryConstants [constantId=" + constantId + ", lateDepositFinePerDay=" + lateDepositFinePerDay
				+ ", maximumBooksLimit=" + maximumBooksLimit + ", bookBorrowDays=" + bookBorrowDays + "]";
	}
	
	
	
	
}
