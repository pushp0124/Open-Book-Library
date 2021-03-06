package com.obl.book.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookAuthor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookAuthorId;
	
	@Column(unique = true, nullable = false)
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
