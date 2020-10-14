package com.obl.mailservice.models;

import java.util.Arrays;
import java.util.List;




public class Book {

	
	private Integer bookId;
	
	
	private String bookTitle;
	
	private BookCategory bookCategory;
	
	private BookAuthor bookAuthor;
	
	private BookPublisher bookPublisher;
	
	private String bookDescription;
	
	private Double bookRating;
	
	private Integer bookCopies;
	
	private Integer bookAvailableCopies;
	
	private Boolean isAvailable;
	
	private Double bookCost;
	
	private String bookEdition;
	

	private String[] bookImages;
	
	
	private List<User> toNotifyUsers;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public BookCategory getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(BookCategory bookCategory) {
		this.bookCategory = bookCategory;
	}

	public BookAuthor getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(BookAuthor bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public BookPublisher getBookPublisher() {
		return bookPublisher;
	}

	public void setBookPublisher(BookPublisher bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	public String getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public Double getBookRating() {
		return bookRating;
	}

	public void setBookRating(Double bookRating) {
		this.bookRating = bookRating;
	}

	public Integer getBookCopies() {
		return bookCopies;
	}

	public void setBookCopies(Integer bookCopies) {
		this.bookCopies = bookCopies;
	}

	public Integer getBookAvailableCopies() {
		return bookAvailableCopies;
	}

	public void setBookAvailableCopies(Integer bookAvailableCopies) {
		this.bookAvailableCopies = bookAvailableCopies;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Double getBookCost() {
		return bookCost;
	}

	public void setBookCost(Double bookCost) {
		this.bookCost = bookCost;
	}

	public String getBookEdition() {
		return bookEdition;
	}

	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
	}

	public String[] getBookImages() {
		return bookImages;
	}

	public void setBookImages(String[] bookImages) {
		this.bookImages = bookImages;
	}

	public List<User> getToNotifyUsers() {
		return toNotifyUsers;
	}

	public void setToNotifyUsers(List<User> toNotifyUsers) {
		this.toNotifyUsers = toNotifyUsers;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookTitle=" + bookTitle + ", bookCategory=" + bookCategory
				+ ", bookAuthor=" + bookAuthor + ", bookPublisher=" + bookPublisher + ", bookDescription="
				+ bookDescription + ", bookRating=" + bookRating + ", bookCopies=" + bookCopies
				+ ", bookAvailableCopies=" + bookAvailableCopies + ", isAvailable=" + isAvailable + ", bookCost="
				+ bookCost + ", bookEdition=" + bookEdition + ", bookImages=" + Arrays.toString(bookImages)
				+ ", toNotifyUsers=" + toNotifyUsers + "]";
	}

	

}

