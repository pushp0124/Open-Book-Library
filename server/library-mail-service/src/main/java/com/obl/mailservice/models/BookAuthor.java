package com.obl.mailservice.models;



public class BookAuthor {

	
	private Integer bookAuthorId;
	
	private String authorName;

	public Integer getBookAuthorId() {
		return bookAuthorId;
	}

	public void setBookAuthorId(Integer bookAuthorId) {
		this.bookAuthorId = bookAuthorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Override
	public String toString() {
		return "BookAuthor [bookAuthorId=" + bookAuthorId + ", authorName=" + authorName + "]";
	}
	
	
	
}
