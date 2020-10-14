package com.obl.book.repos;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.obl.book.models.BookTransaction;
import com.obl.book.models.TopBorrowedBooks;

@Repository
public interface BookTransactionRepo extends JpaRepository<BookTransaction, Integer> {
	  @Query("select T from BookTransaction T where T.borrowedToUser.userId=:userId")
      public Page<BookTransaction> viewBooksBorrowedById(@Param("userId") Integer userId, Pageable pageable);
	  
	  
	  @Query("select T from BookTransaction T where T.bookStatus='BORROWED'")
      public Page<BookTransaction> viewBorrowedBooks(Pageable pageable);
	  
	  
	  @Query("select T from BookTransaction T where T.bookStatus='BORROWED'")
      public List<BookTransaction> viewBorrowedBooksWithSort(Sort sort);
	  
	  
	  @Query("select T from BookTransaction T where T.bookStatus='BORROWED'")
      public List<BookTransaction> viewBorrowedBooks();
	  
	  
	  @Query("select T from BookTransaction T where T.bookStatus='BORROWED' and T.returnDate=:returnDate")
      public Page<BookTransaction> viewBorrowedBooksOnDateWithPagination(@Param("returnDate") Date returnDate, Pageable pageable);
	  
	  
	  @Query("select T from BookTransaction T where T.bookStatus='BORROWED' and T.returnDate=:returnDate")
      public List<BookTransaction> viewBorrowedBooksOnDateWithSort(@Param("returnDate") Date returnDate, Sort sort);
	  
	  
	  @Query("select T from BookTransaction T where T.bookStatus='BORROWED' and T.returnDate=:returnDate")
      public List<BookTransaction> viewBorrowedBooksOnDate(@Param("returnDate") Date returnDate);
	  
	  
	  
	  @Query("select T from BookTransaction T where T.bookStatus='LOST' and T.borrowedToUser.userId=:userId")
      public List<BookTransaction> getLostBooksByUser(@Param("userId") Integer userId);
	  
	  
	  @Query("select DISTINCT(T.returnDate) from BookTransaction T where T.bookStatus='BORROWED'")
      public List<Date> getBorrowedBooksDate();
	  
	  
	  @Query("select count(*) from BookTransaction T where T.bookStatus='LOST'")
      public Integer getTotalLostBooks();
	  
	  
	  @Query("select count(*) from BookTransaction T where T.bookStatus='BORROWED'")
      public Integer getTotalBorrowedBooks();
	  
	  
	  @Query("select new com.obl.book.models.TopBorrowedBooks(count(*), T.borrowedBook) from BookTransaction T group by T.borrowedBook.bookId order by count(*) desc" )
	  public List<TopBorrowedBooks> getTopBorrowedBooks();
	  
	  @Query("select T from BookTransaction T where T.borrowedToUser.userId=:userId and T.bookStatus='BORROWED' and T.borrowedBook.bookId=:bookId")
	  public Optional<BookTransaction> checkIfBookIsBorrowed(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
	  
	  @Query("select count(T) from BookTransaction T where T.borrowedToUser.userId=:userId and T.bookStatus='BORROWED'")
	  public Integer countCurrentlyBorrowedBooks(@Param("userId") Integer userId);
	  
	  
	  
}
