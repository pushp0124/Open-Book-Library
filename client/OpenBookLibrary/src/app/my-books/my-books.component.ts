
import { LibraryBookService } from '../book-service';
import { LibraryAuthService } from '../auth-service';
import { IssueBook } from '../model/issueBook';
import { Router } from '@angular/router';
import { Component, ViewChild, AfterViewInit, OnInit, ViewEncapsulation } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';


@Component({
  selector: 'app-my-books',
  templateUrl: './my-books.component.html',
  styleUrls: ['./my-books.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MyBooksComponent implements OnInit, AfterViewInit {


  errorMessage: string;
  successMessage: string;


  displayedColumns: string[] = ["issuedBook.bookTitle", "issuedBook.bookAuthor.authorName", "issuedBook.bookCategory.category", "issueDate", "returnDate", "actions"]

  data: IssueBook[] = [];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;

  constructor(private authService: LibraryAuthService, private bookService: LibraryBookService, private router: Router) { }
 
  ngOnInit() {

  }
  ngAfterViewInit() {

    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.bookService.viewIssuedBooks
            (this.authService.loggedInUser.userId, this.paginator.pageIndex, 5, this.sort.active, this.sort.direction);
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
      ).subscribe(data => this.data = data);
  }

  viewBookDetail(row: IssueBook) {
    this.bookService.detailAboutBook = row;
    console.log(this.bookService.detailAboutBook);
    this.router.navigate(['/userhomepage/book-detail']);
  }
  reIssueBook(row: IssueBook) {
    let selectedBook = row.issuedBook;
    this.bookService.issueBook(selectedBook.bookId).subscribe((issueBook: IssueBook) => {
      this.ngOnInit();
      this.successMessage = "Book Re-Issued successfully, its return date is " + issueBook.returnDate;
    }, (bookIssueError) => {
      console.log(bookIssueError);
      this.errorMessage = bookIssueError.error.message;
    })
  }


 
}