package com.obl.book.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obl.book.models.BookPublisher;


@Repository
public interface BookPublisherRepo extends JpaRepository<BookPublisher, Integer> {

}
