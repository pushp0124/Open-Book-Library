package com.obl.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obl.demo.bean.BookCategory;


@Repository
public interface BookCategoryDao extends JpaRepository<BookCategory, Integer>{

}