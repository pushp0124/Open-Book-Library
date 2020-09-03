package com.obl.book.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obl.book.models.BookCategory;

@Repository
public interface BookCategoryRepo extends JpaRepository<BookCategory, Integer>{

}
