package com.obl.demo.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.obl.demo.bean.Book;
import com.obl.demo.bean.BookAuthor;
import com.obl.demo.bean.BookCategory;
import com.obl.demo.bean.BookPublisher;
import com.obl.demo.exception.OpenBookLibraryException;
import com.obl.demo.service.OpenBookLibraryAdminService;

@CrossOrigin("http://localhost:4200")
@RestController
public class OpenBookLibraryAdminController {


	@Autowired
	OpenBookLibraryAdminService adminService;
	
	@PostMapping("/add/book")
	public Book addBook(@RequestBody Book book) throws OpenBookLibraryException {
		return adminService.addBook(book);
	}


	@PostMapping("/add/category")
	public BookCategory addBookCategory(@RequestBody BookCategory category) throws OpenBookLibraryException {
		return adminService.addBookCategory(category);
	}
	
	@PostMapping("/add/author")
	public BookAuthor addAuthor(@RequestBody BookAuthor author) throws OpenBookLibraryException {
		return adminService.addAuthor(author);
	}
	
	@PostMapping("/add/publisher")
	public BookPublisher addPublisher(@RequestBody BookPublisher publisher) throws OpenBookLibraryException {
		return adminService.addPublisher(publisher);
	}
	
	@PutMapping("/update/book")
	public Book updateBook(@RequestBody Book book) throws OpenBookLibraryException {
		return adminService.updateBook(book);
	}
}
