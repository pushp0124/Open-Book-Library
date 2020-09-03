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

  errorMessage : string;
  successMessage : string;
  books : Book[];
  loggedInUserId : number;
  issuedBooks : Book[] = [];
  constructor(private bookService : LibraryBookService, private authService : LibraryAuthService) { }

  ngOnInit() {
    this.loggedInUserId = this.authService.loggedInUser.userId;
    this.bookService.viewBooks().subscribe((books) => {
        this.books = books;
    })
  }

  issueBook(index : number){
     this.bookService.issueBook(this.books[index].bookId).subscribe((issueBook : IssueBook) => {
        this.successMessage = "Book Issued successfully, its return date is " + issueBook.returnDate;
        this.issuedBooks.push(issueBook.issuedBook);
        this.books.splice(index,1);
       
      },(bookIssueError) => {
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
