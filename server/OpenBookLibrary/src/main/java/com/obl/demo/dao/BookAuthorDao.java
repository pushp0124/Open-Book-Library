package com.obl.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obl.demo.bean.BookAuthor;


@Repository
public interface BookAuthorDao extends JpaRepository<BookAuthor, Integer>{

}
