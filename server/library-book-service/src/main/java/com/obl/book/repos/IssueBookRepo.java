package com.obl.book.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.obl.book.models.IssueBook;

@Repository
public interface IssueBookRepo extends JpaRepository<IssueBook, Integer> {
	  @Query("select B from IssueBook B where B.issuedToUser.userId=:userId")
      public Page<IssueBook> viewBooksIssuedById(@Param("userId") Integer userId, Pageable pageable);
	  
	  
	  @Query("select B from IssueBook B where B.bookStatus!='RETURNED'")
      public Page<IssueBook> viewIssuedBooks(Pageable pageable);
}
