import { Component, OnInit } from '@angular/core';
import { LibraryBookService } from '../book-service';
import { LibraryAuthService } from '../auth-service';
import { Book } from '../model/book';
import { IssueBook } from '../model/issueBook';
import { Router } from '@angular/router';
import { BookCategory } from '../model/bookCategory';
import { SearchModel } from '../model/searchModel';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user-home-child',
  templateUrl: './user-home-child.component.html',
  styleUrls: ['./user-home-child.component.css']
})
export class UserHomeChildComponent implements OnInit {

  errorMessage: string;
  successMessage: string;
  books: Book[];
  loggedInUserId: number;
  issuedBooks: Book[] = [];
  bookCategories : string[] = ["Action And Adventure", "Biographies Diaries & True Accounts", "Comics & Mangas","Crime, Thriller & Mystery","History", "Technology"];
  constructor(private bookService: LibraryBookService, private authService: LibraryAuthService, private router : Router) { }

  searchModel : SearchModel;
  subscription : Subscription;

  ngOnInit() {
    this.loggedInUserId = this.authService.loggedInUser.userId;
   

    this.subscription = this.bookService.currentSearch$.subscribe(search => {
      this.searchModel = search
      console.log(this.searchModel)
      if(this.searchModel.searchText == undefined) {
        this.bookService.viewBooks().subscribe((books) => {
           this.books = books;
        })
      }
   
      if(this.searchModel.selectedOption == 0) {
        console.log("All Search")
          this.bookService.viewBooksByAllSearch(this.searchModel.searchText).subscribe((books) => {
              this.books = books;
          })
      } else if(this.searchModel.selectedOption == 1) {
        console.log("Title Search")
        this.bookService.viewBooksByTitleSearch(this.searchModel.searchText).subscribe((books) => {
          this.books = books;
      })

      } else if(this.searchModel.selectedOption == 2) {
        console.log("Author Search")
        this.bookService.viewBooksByAuthorSearch(this.searchModel.searchText).subscribe((books) => {
          this.books = books;
      })

      } else if(this.searchModel.selectedOption == 3) {
        console.log("Category Search")
        this.bookService.viewBooksByCategorySearch(this.searchModel.searchText).subscribe((books) => {
          this.books = books;
      })

      } else if(this.searchModel.selectedOption == 4)  {
        console.log("Publisher")
         this.bookService.viewBooksByPublisherSearch(this.searchModel.searchText).subscribe((books) => {
          this.books = books;
      })
      }
    })

   
    
  }

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }

  viewBookDetail(index : number) {
      this.bookService.detailAboutBook.issuedBook = this.books[index];
      this.router.navigate(['/userhomepage/book-detail']);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }


  
}
