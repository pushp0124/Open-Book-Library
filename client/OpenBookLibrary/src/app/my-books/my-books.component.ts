import { LibraryBookService} from '../book-service';
import { LibraryAuthService } from '../auth-service';
import { BookTransaction } from '../model/bookTransaction';
import { Router } from '@angular/router';
import { Component, ViewChild, AfterViewInit, OnInit, ViewEncapsulation } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap, first } from 'rxjs/operators';
import { HttpParams } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { BookTransactionStatus } from '../model/bookTransactionStatus';
import { LibraryConstants } from '../model/libraryConstants';

@Component({
  selector: 'app-my-books',
  templateUrl: './my-books.component.html',
  styleUrls: ['./my-books.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MyBooksComponent implements OnInit, AfterViewInit {


  errorMessage: string;
  successMessage: string;


  displayedColumns: string[] = ["borrowedBook.bookTitle", "borrowedBook.bookAuthor.authorName", "borrowedBook.bookCategory.category", "borrowedDate", "returnDate", "bookStatus", "fineTillDate", "actions"]

  data: BookTransaction[] = [];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  constants: LibraryConstants;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;

  constructor(private authService: LibraryAuthService, private bookService: LibraryBookService, private router: Router, private datePipe: DatePipe) { }

  ngOnInit() {
    this.authService.getLibraryConstants().subscribe((constants) => {
      this.constants = constants;

    }, (constantsError) => {
      this.errorMessage = constantsError
    })
  }
  ngAfterViewInit() {

    // If the user changes the sort order, reset back to the first page.
    this.data = [];
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          let params = new HttpParams()
            .append('pageNo', this.paginator.pageIndex.toString())
            .append('pageSize', '5')
            .append('sortByColumn', this.sort.active)
            .append('sortDirection', this.sort.direction)
          return this.bookService.viewBorrowedBooks
            (this.authService.getAuthResponse().loggedInUserId, params);
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.total_count;

          return data.books;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => {
        this.data = data
        data.forEach((bookTransaction: BookTransaction) => {
          let diff: number = 0;
          if (bookTransaction.bookStatus == BookTransactionStatus.BORROWED) {
            let expectedReturnDateTime = new Date(bookTransaction.returnDate).getTime()
            diff = new Date().getTime() - expectedReturnDateTime

            // today's date  - expected return date

          }

          // else if (bookTransaction.bookStatus == BookTransactionStatus.RETURNED) {
          //   let returnDateTime = new Date(bookTransaction.returnDate).getTime()
          //   let expectedReturnDateTime = new Date(bookTransaction.borrowedDate).getTime() + (1000 * 60 * 60 * 24 * this.constants.bookBorrowDays)
          //   diff = returnDateTime - expectedReturnDateTime   
          //   // return date - expected return date            
          // }

          let diffInDays = Math.floor(diff / (1000 * 3600 * 24));
          if (diffInDays > 0) {
            bookTransaction.fineTillDate = (diffInDays) * this.constants.lateDepositFinePerDay
          } else {
            bookTransaction.fineTillDate = 0;
          }
        })

      });
  }

  viewBookDetail(transaction: BookTransaction) {

    this.router.navigate(['/my-books/book/' + transaction.borrowedBook.bookId]);
  }


  reIssueBook(row: BookTransaction) {
    let selectedBook = row.borrowedBook;

    let userId = this.authService.getAuthResponse().loggedInUserId;
    let transformedDate = this.datePipe.transform(new Date(), this.bookService.dateFormatter);
    this.bookService.borrowBook(selectedBook.bookId, userId, transformedDate).subscribe((bookTransaction: BookTransaction) => {

      let returnDate = this.datePipe.transform(bookTransaction.returnDate, 'dd-MM-yyyy')
      this.successMessage = "Book Re-Borrowed successfully, its return date is " + returnDate;
    }, (bookIssueError) => {
      console.log(bookIssueError);
      this.errorMessage = bookIssueError;
    })
  }


  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }

}