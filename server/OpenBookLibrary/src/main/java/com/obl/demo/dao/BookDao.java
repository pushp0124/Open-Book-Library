package com.obl.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obl.demo.bean.Book;

@Repository
public interface BookDao extends JpaRepository<Book, Integer>{

}
