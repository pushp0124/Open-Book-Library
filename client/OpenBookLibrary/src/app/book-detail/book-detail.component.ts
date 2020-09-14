import { Component, OnInit } from '@angular/core';
import { Book } from '../model/book';
import { IssueBook } from '../model/issueBook';
import { User } from '../model/user';
import { LibraryBookService } from '../book-service';
import { LibraryAuthService } from '../auth-service';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  errorMessage: string;
  successMessage: string;
  loggedInUserId: number;
  detailAboutBook: IssueBook;
  constructor(private bookService: LibraryBookService, private authService: LibraryAuthService) { }

  ngOnInit() {
    this.loggedInUserId = this.authService.loggedInUser.userId;
    this.detailAboutBook = this.bookService.detailAboutBook;

  }

  issueBook() {
    this.bookService.issueBook(this.detailAboutBook.issuedBook.bookId).subscribe((issueBook: IssueBook) => {
      console.log("From IssueBook" + issueBook);
      this.successMessage = "Book Issued successfully, its return date is " + issueBook.returnDate;
    }, (bookIssueError) => {
      console.log(bookIssueError);
      this.errorMessage = bookIssueError.error.message;
    })
  }

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }

}
