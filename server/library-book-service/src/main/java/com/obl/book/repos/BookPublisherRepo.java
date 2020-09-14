package com.obl.book.repos;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.obl.book.models.BookPublisher;


@Repository
public interface BookPublisherRepo extends JpaRepository<BookPublisher, Integer> {

	@Query("select P from BookPublisher P where LOWER(P.publisherName)=:publisherName")
	public BookPublisher checkIfPublisherExistsByName(@Param("publisherName") String publisherName);
	
	 @Query("select P from BookPublisher P")
     public Page<BookPublisher> viewPublishers(Pageable pageable);
}
