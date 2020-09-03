package com.obl.book.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obl.book.models.IssueBook;

@Repository
public interface IssueBookRepo extends JpaRepository<IssueBook, Integer> {

}
