import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { BookTransaction } from '../model/bookTransaction';
import { LibraryBookService } from '../book-service';
import { DatePipe } from '@angular/common';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, of as observableOf, Observable, ReplaySubject, Subject } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { HttpParams } from '@angular/common/http';
import { User } from '../model/user';
import { LibraryAuthService } from '../auth-service';
import { MatDialog } from '@angular/material';
import { BookLostComponent } from '../book-lost/book-lost.component';
import { BookTransactionStatus } from '../model/bookTransactionStatus';
import { LibraryConstants } from '../model/libraryConstants';

@Component({
  selector: 'app-user-book-report',
  templateUrl: './user-book-report.component.html',
  styleUrls: ['./user-book-report.component.css']
})
export class UserBookReportComponent implements OnInit, AfterViewInit {


  currentDate: string;
  errorMessage: string;
  successMessage: string;
  selectedBookingType: number;

  patrons: User[] = []
  patronsEmail: string[] = []
  filteredPatronsEmail: Observable<string[]>;

  constants : LibraryConstants;

  displayedColumns: string[] = ["borrowedBook.bookTitle", "borrowedBook.bookAuthor.authorName", "borrowedDate", "returnDate", "fineTillDate", "bookStatus", "actions"]

  data: BookTransaction[] = [];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;


  isUserEmailTyped = true
  constructor(private bookService: LibraryBookService, private authService: LibraryAuthService, private _formBuilder: FormBuilder, private matDialog: MatDialog) { }

  patronFormGroup: FormGroup;

  ngOnInit() {


    this.patronFormGroup = this._formBuilder.group({
      patronEmailCtrl: ['', [Validators.required, Validators.email]]
    })

    this.authService.viewAllPatrons().subscribe((users) => {
      this.patrons = users
      this.patrons.forEach(patron => {
        this.patronsEmail.push(patron.userEmail);
      });
      this.filteredPatronsEmail = this.patronEmailCtrl.valueChanges
        .pipe(
          startWith(''),
          map(value => this._filterPatronsEmail(value))
        );

        this.getLibraryConstants();
    }, (patronsError) => {
      this.errorMessage = patronsError;
    })
    this.selectedBookingType = 1;


  }

  getLibraryConstants() {
    this.authService.getLibraryConstants().subscribe((constants) => {
      this.constants = constants;
    }, (error) => {
      this.errorMessage = error
    })
  }
  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
  }
  private _filterPatronsEmail(patronEmail: string): string[] {
    const filterValue = patronEmail.toLowerCase();

    let filteredPatronsEmail = this.patronsEmail.filter(email => {
      return email.toLowerCase().includes(filterValue)
    })
    return filteredPatronsEmail;
  }

  get patronEmailCtrl(): AbstractControl {
    return this.patronFormGroup.controls['patronEmailCtrl'];
  }
  viewPatronReport() {
    // If the user changes the sort order, reset back to the first page.
    let isEmailPresent = false
    this.data = []
    this.patrons.forEach((patron) => {
      if (patron.userEmail === this.patronEmailCtrl.value) {
        isEmailPresent = true
        this.isUserEmailTyped = true

        merge(this.sort.sortChange, this.paginator.page)
          .pipe(
            startWith({}),
            switchMap(() => {
              this.isLoadingResults = true;
              let params = new HttpParams()
                .append('pageNo', this.paginator.pageIndex.toString())
                .append('pageSize', '10')
                .append('sortByColumn', this.sort.active)
                .append('sortDirection', this.sort.direction)
              return this.bookService.viewBorrowedBooks
                (patron.userId, params);
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
    })

    if (!isEmailPresent) {
      this.errorMessage = "No such mail is registered"
    }
  }
  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }


  depositBook(issueBook: BookTransaction) {
    this.bookService.depositBorrowedBook(issueBook.transactionId).subscribe((_issueBook) => {
      // let index = 0
      // let _data = this.data
      // console.log(_issueBook)
      //   _data.forEach((issueBook) => {

      //     if(issueBook.transactionId == _issueBook.transactionId) {
      //         _data[index] = _issueBook
      //     }
      //     index ++;
      //   })
      this.paginator._changePageSize(10);
      this.successMessage = "Book Deposited Successfully!"

    }, (depositError) => {
      this.errorMessage = depositError;
    })
  }

  bookLostByUser(borrowedBook: BookTransaction) {
    this.bookService.bookLostByUser(borrowedBook.transactionId).subscribe((lostBorrowedBooks) => {
      console.log(lostBorrowedBooks)
      let index = 0
      this.data.forEach((bookTransaction) => {

        if (bookTransaction.transactionId == borrowedBook.transactionId) {
          this.data[index].bookStatus = BookTransactionStatus.LOST
        }
        index++;
      })

      let dialogRef = this.matDialog.open(BookLostComponent, {
        data: lostBorrowedBooks
      });
    }, (lostIssueError) => {
      this.errorMessage = lostIssueError
    })
  }

}
