package com.obl.demo.service;

import com.obl.demo.bean.Book;
import com.obl.demo.bean.BookAuthor;
import com.obl.demo.bean.BookCategory;
import com.obl.demo.bean.BookPublisher;
import com.obl.demo.exception.OpenBookLibraryException;


public interface OpenBookLibraryAdminService {


	public BookCategory addBookCategory(BookCategory category) throws OpenBookLibraryException;
	 
	public BookAuthor addAuthor(BookAuthor author) throws OpenBookLibraryException;
	
	public BookPublisher addPublisher(BookPublisher publisher) throws OpenBookLibraryException;
	
	public Book addBook(Book book) throws OpenBookLibraryException;
	
	public Book updateBook(Book book) throws OpenBookLibraryException;
}
