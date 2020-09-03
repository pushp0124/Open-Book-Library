package com.obl.book.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obl.book.models.BookAuthor;

@Repository
public interface BookAuthorRepo extends JpaRepository<BookAuthor, Integer>{

}
