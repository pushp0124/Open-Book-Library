package com.obl.book.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obl.book.models.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{

}
