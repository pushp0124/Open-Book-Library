
import { BookTransaction } from '../model/bookTransaction';
import { Component, ViewChild, AfterViewInit, OnInit, ViewEncapsulation, ElementRef } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, of as observableOf, fromEvent } from 'rxjs';
import { catchError, map, startWith, switchMap, debounceTime, distinctUntilChanged, tap, filter } from 'rxjs/operators';

import { LibraryBookService } from '../book-service';
import { IssueBookApi } from '../model/bookTransactionDto';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource, MatDialog } from '@angular/material';
import { HttpParams } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { MatCalendarCellCssClasses } from '@angular/material';
import { BookLostComponent } from '../book-lost/book-lost.component';
import { MailService } from '../mail.service';
import { BookTransactionStatus } from '../model/bookTransactionStatus';
import { LibraryAuthService } from '../auth-service';
import { LibraryConstants } from '../model/libraryConstants';

@Component({
  selector: 'app-deposit-book',
  templateUrl: './deposit-book.component.html',
  styleUrls: ['./deposit-book.component.css']
})
export class DepositBookComponent implements OnInit, AfterViewInit {


  data: BookTransaction[] = [];

  filteredData: BookTransaction[] = [];
  displayedColumns: string[] = ["select", "borrowedBook.bookTitle", "borrowedToUser.userEmail", "borrowedDate", "returnDate", "fineTillDate", "actions"]

  errorMessage: string = undefined;
  successMessage: string = undefined;
  dataSource: MatTableDataSource<BookTransaction>;
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  @ViewChild(MatSort, { static: false }) sort: MatSort;

  selection = new SelectionModel<BookTransaction>(true, []);

  minDate: Date;

  issuedBooksDate: Date[];

  selectedDate : Date;

  constants : LibraryConstants;

  constructor(private bookService: LibraryBookService, private datePipe: DatePipe, private matDialog : MatDialog,
    private mailService : MailService, private authService : LibraryAuthService) { }

  ngOnInit() {
    this.authService.getLibraryConstants().subscribe((constants) => {
      this.constants = constants;
      this.bookService.getBorrowedBooksDate().subscribe((dates) => {
        dates = dates.map((date) => {
         return new Date(date)
       })
       this.issuedBooksDate = dates.sort((a, b) => {
         if (a > b) {
           return 1;
         }
         if (a < b) {
           return -1;
         }
         return 0;
       })
       this.minDate = this.issuedBooksDate[0];
       this.onReportDateChange(this.minDate);
     })
    }, (error) => {
      this.errorMessage = error
    })
    
   

  }

  ngAfterViewInit() {

    // // let filterObservable = fromEvent(this.filterInput.nativeElement, 'keyup')
    // //   .pipe(
    // // debounceTime(150),
    // //     distinctUntilChanged(),
    // //     tap(() => {
    // //       this.paginator.pageIndex = 0;
    // //     })
    // //   )
    // // filterObservable.subscribe();
    // // If the user changes the sort order, reset back to the first page.
    // // this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    // merge(this.sort.sortChange)
    //   .pipe(
    //     startWith({}),
    //     switchMap(() => {
    //       this.isLoadingResults = true;
    //       let params = new HttpParams()
    //         .append('sortByColumn', this.sort.active)
    //         .append('sortDirection', this.sort.direction)
    //       return this.bookService.viewAllBorrowedBooks
    //         (params);
    //     }),
    //     map(data => {
    //       // Flip flag to show that loading has finished.
    //       this.isLoadingResults = false;
    //       this.isRateLimitReached = false;
    //       this.resultsLength = data.total_count;
    //       console.log(data)
    //       return data.books;
    //     }),
    //     catchError(() => {
    //       this.isLoadingResults = false;
    //       this.isRateLimitReached = true;
    //       return observableOf([]);
    //     })
    //   ).subscribe(data => this.data = data);
  }

  applyFilter(filterValue: string) {

    const _filterValue = filterValue.toLowerCase();
    this.selection.clear();
    this.filteredData =  this.data.filter((issueBook) => {
      if(issueBook.borrowedBook.bookTitle.toLowerCase().includes(_filterValue) || 
      issueBook.borrowedToUser.userName.toLowerCase().includes(_filterValue)
      || issueBook.borrowedDate.toString().toLowerCase().includes(_filterValue)
      || issueBook.returnDate.toString().toLowerCase().includes(_filterValue) ) {
          return true;
      } 
      return false;
    })
  }


  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.data.forEach(row => this.selection.select(row));
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: BookTransaction): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.transactionId + 1}`;
  }


  depositBook(issueBook: BookTransaction) {
    this.bookService.depositBorrowedBook(issueBook.transactionId).subscribe((issueBook) => {
     
      this.successMessage = "Book Deposited sucessfully";
      this.ngOnInit();
    },
      (bookDepositionError) => {
        this.errorMessage = bookDepositionError;
      })
  }

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }


  onReportDateChange(selectedDate: Date) {
    this.selectedDate = selectedDate;
    let transformedDate = this.datePipe.transform(selectedDate, this.bookService.dateFormatter);
    console.log(transformedDate, selectedDate)
    this.selection.clear();
    merge(this.sort.sortChange)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          let params = new HttpParams()
            .append('returnDate', transformedDate)
          return this.bookService.viewAllBorrowedBooks
            (params);
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
          this.filteredData = data
        })
      });

  }

  dateClass = (date: Date) : MatCalendarCellCssClasses => {

    let transformedDate = this.datePipe.transform(date, this.bookService.dateFormatter);
    let isFound = false;
    this.issuedBooksDate.forEach((_date) => {
      let transformedIssueDate = this.datePipe.transform(_date, this.bookService.dateFormatter);
      if(transformedDate == transformedIssueDate) {
        isFound = true;
      }
    })

    return isFound ? 'custom-date-class' : ''

  }

  notifyDelayedMembers() {
       let issueBooks : BookTransaction[] = this.selection.selected
       this.mailService.notifyDelayedMembers(issueBooks).subscribe(() => {
         this.successMessage = "Patrons notified successfully"
       }, (mailError) => {
         this.errorMessage = mailError
       })
  }

  bookLostByUser(borrowedBook : BookTransaction) {
    this.bookService.bookLostByUser(borrowedBook.transactionId).subscribe((lostBorrowedBooks) => {
      console.log(lostBorrowedBooks)
      let index = 0
          this.data.forEach((issueBook) => {

            if(issueBook.transactionId == borrowedBook.transactionId) {
                this.data[index].bookStatus = BookTransactionStatus.LOST;
            }
            index ++;
          })

          let dialogRef = this.matDialog.open(BookLostComponent, {
            data: lostBorrowedBooks
          });
    }, (lostIssueError) => {
        this.errorMessage = lostIssueError
    })
  }

}
