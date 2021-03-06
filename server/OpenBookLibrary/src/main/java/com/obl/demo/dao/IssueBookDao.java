package com.obl.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obl.demo.bean.IssueBook;

@Repository
public interface IssueBookDao  extends JpaRepository<IssueBook, Integer>{

}
