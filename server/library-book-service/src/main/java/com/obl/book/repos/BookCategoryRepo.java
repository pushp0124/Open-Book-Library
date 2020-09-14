package com.obl.book.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.obl.book.models.BookCategory;

@Repository
public interface BookCategoryRepo extends JpaRepository<BookCategory, Integer>{

	 @Query("select C from BookCategory C")
     public Page<BookCategory> viewCategories(Pageable pageable);
}
