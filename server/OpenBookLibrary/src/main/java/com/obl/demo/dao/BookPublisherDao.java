package com.obl.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obl.demo.bean.BookPublisher;

@Repository
public interface BookPublisherDao extends JpaRepository<BookPublisher, Integer>{

}
