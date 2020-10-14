package com.obl.book.repos;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.obl.book.models.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{

	
	@Query("select B from Book B where LOWER(B.bookTitle) like :title and ( B.bookCategory.categoryId=:categoryId or :categoryId=0 ) ")
	public List<Book> searchByBookTitle(@Param("title") String bookTitleSearchString, @Param("categoryId") Integer categoryId);
	
	@Query("select B from Book B where LOWER(B.bookAuthor.authorName) like :author and (B.bookCategory.categoryId=:categoryId or :categoryId=0)")
	public List<Book> searchByBookAuthor(@Param("author") String bookAuthorSearchString, @Param("categoryId") Integer categoryId);
	
	@Query("select B from Book B where LOWER(B.bookPublisher.publisherName) like :publisher  and ( B.bookCategory.categoryId=:categoryId or :categoryId=0 )")
	public List<Book> searchByBookPublisher(@Param("publisher") String bookPublisherSearchString, @Param("categoryId") Integer categoryId);
	
	@Query("select B from Book B where LOWER(B.bookCategory.category) like :category and ( B.bookCategory.categoryId=:categoryId or :categoryId=0 )")
	public List<Book> searchByBookCategory(@Param("category") String bookCategorySearchString, @Param("categoryId") Integer categoryId);
	
	@Query("select B from Book B where LOWER(B.bookCategory.category) like :searchString or LOWER(B.bookTitle) like :searchString or LOWER(B.bookAuthor.authorName) like :searchString or LOWER(B.bookPublisher.publisherName) like :searchString  and ( B.bookCategory.categoryId=:categoryId or :categoryId=0 )")
	public List<Book> searchByAll(@Param("searchString") String bookAllSearchString, @Param("categoryId") Integer categoryId); //title + author + publisher + category
	
	
	@Query("select sum(B.bookCost * B.bookCopies) from Book B where B.isAvailable=true")
	public Long getTotalPriceOfAvailableBooks();
	
	
	@Query("select sum(B.bookCopies) from Book B")
	public Integer getTotalBooks();
	
	
	@Query("select sum(B.bookAvailableCopies) from Book B")
	public Integer getTotalAvailableBooks();
	
	@Query("select B from Book B where B.bookAuthor.bookAuthorId=:authorId")
	public Page<Book> getBooksOfAuthor(@Param("authorId") Integer authorId, Pageable paging);
	
	@Query("select B from Book B where B.bookCategory.categoryId=:categoryId or :categoryId=0")
	public Page<Book> getBooksOfCategory(@Param("categoryId") Integer categoryId, Pageable paging);
	
	@Query("select B from Book B where B.bookCategory.categoryId=:categoryId or :categoryId=0")
	public List<Book> getBooksOfCategoryBySort(@Param("categoryId") Integer categoryId, Sort sort);
	
	
	
}
