package com.obl.book.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.obl.book.models.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{

	
	@Query("select B from Book B where LOWER(B.bookTitle) like :title")
	public List<Book> searchByBookTitle(@Param("title") String bookTitleSearchString);
	
	@Query("select B from Book B where LOWER(B.bookAuthor.authorName) like :author")
	public List<Book> searchByBookAuthor(@Param("author") String bookAuthorSearchString);
	
	@Query("select B from Book B where LOWER(B.bookPublisher.publisherName) like :publisher")
	public List<Book> searchByBookPublisher(@Param("publisher") String bookPublisherSearchString);
	
	@Query("select B from Book B where LOWER(B.bookCategory.category) like :category")
	public List<Book> searchByBookCategory(@Param("category") String bookCategorySearchString);
	
	@Query("select B from Book B where LOWER(B.bookCategory.category) like :searchString or LOWER(B.bookTitle) like :searchString or LOWER(B.bookAuthor.authorName) like :searchString or LOWER(B.bookPublisher.publisherName) like :searchString")
	public List<Book> searchByAll(@Param("searchString") String bookAllSearchString); //title + author + publisher + category
	
	
	
}
