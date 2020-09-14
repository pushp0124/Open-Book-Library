package com.obl.book.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.obl.book.models.BookAuthor;

@Repository
public interface BookAuthorRepo extends JpaRepository<BookAuthor, Integer>{
	@Query("select A from BookAuthor A where LOWER(A.authorName)=:authorName")
	public BookAuthor checkIfAuthorExistsByName(@Param("authorName") String authorName);
	
	 @Query("select A from BookAuthor A")
     public Page<BookAuthor> viewAuthors(Pageable pageable);
}
