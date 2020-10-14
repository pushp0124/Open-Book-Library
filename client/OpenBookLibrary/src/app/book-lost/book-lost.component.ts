import { Component, OnInit, Inject } from '@angular/core';
import { LibraryAuthService } from '../auth-service';
import { BookTransaction } from '../model/bookTransaction';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-book-lost',
  templateUrl: './book-lost.component.html',
  styleUrls: ['./book-lost.component.css']
})
export class BookLostComponent implements OnInit {

  userEmailId : string
  lostBooksCount : number
  lostBooksCost : number = 0
  userId : number;

  errorMessage : string;
  successMessage : string;
  constructor(@Inject(MAT_DIALOG_DATA) public borrowedBooks : BookTransaction[], private authService : LibraryAuthService) { }

  ngOnInit() {
      this.lostBooksCount = this.borrowedBooks.length
      this.borrowedBooks.forEach((book) => {
          this.userId = book.borrowedToUser.userId
          this.userEmailId = book.borrowedToUser.userEmail
          this.lostBooksCost += book.borrowedBook.bookCost
      })
  }

  

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }

  disableUser() {
    this.authService.disableUser(this.userId).subscribe((isDisabled) => {
      if(isDisabled) {
        this.successMessage = "User is disabled, can't perform any actions!"
      } 
        
    }, (diableUserError) => {
        this.errorMessage = diableUserError
    })
  }
}
