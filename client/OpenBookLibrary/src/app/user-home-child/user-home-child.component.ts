import { Component, OnInit } from '@angular/core';
import { LibraryBookService } from '../book-service';
import { LibraryAuthService } from '../auth-service';
import { Book } from '../model/book';
import { BookTransaction } from '../model/bookTransaction';
import { Router, ActivatedRoute } from '@angular/router';
import { BookCategory } from '../model/bookCategory';
import { SearchModel } from '../model/searchModel';
import { Subscription } from 'rxjs';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-user-home-child',
  templateUrl: './user-home-child.component.html',
  styleUrls: ['./user-home-child.component.css']
})
export class UserHomeChildComponent implements OnInit {

  errorMessage: string;
  successMessage: string;
  books: Book[] = [];
  loggedInUserId: number;

  stars: number[] = [1, 2, 3, 4, 5];
 
  selectedBookCategoryId : number = 0;
  constructor(private bookService: LibraryBookService, private authService: LibraryAuthService, private router: Router, private route : ActivatedRoute) { }

  searchModel: SearchModel;
  subscription: Subscription;

  ngOnInit() {

    
    this.loggedInUserId = this.authService.getAuthResponse().loggedInUserId;
    const categoryId = this.route.snapshot.paramMap.get('categoryId');
    this.selectedBookCategoryId = isNaN(+categoryId) ? 0 : +categoryId
    this.subscription = this.bookService.currentSearch$.subscribe(search => {
      this.searchModel = search
      console.log(this.searchModel)
      if (this.searchModel.searchText == undefined) {

        this.bookService.viewBooks(this.selectedBookCategoryId).subscribe((booksApi) => {
          this.books = booksApi.books;
        })
      }

      if (this.searchModel.selectedOption == 0) {
        console.log("All Search")
        this.bookService.viewBooksByAllSearch(this.selectedBookCategoryId, this.searchModel.searchText).subscribe((books) => {
          this.books = books;
        })
      } else if (this.searchModel.selectedOption == 1) {
        console.log("Title Search")
        this.bookService.viewBooksByTitleSearch(this.selectedBookCategoryId,this.searchModel.searchText).subscribe((books) => {
          this.books = books;
        })

      } else if (this.searchModel.selectedOption == 2) {
        console.log("Author Search")
        this.bookService.viewBooksByAuthorSearch(this.selectedBookCategoryId,this.searchModel.searchText).subscribe((books) => {
          this.books = books;
        })

      } else if (this.searchModel.selectedOption == 3) {
        console.log("Category Search")
        this.bookService.viewBooksByCategorySearch(this.selectedBookCategoryId,this.searchModel.searchText).subscribe((books) => {
          this.books = books;
        })

      } else if (this.searchModel.selectedOption == 4) {
        console.log("Publisher")
        this.bookService.viewBooksByPublisherSearch(this.selectedBookCategoryId,this.searchModel.searchText).subscribe((books) => {
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

  viewBookDetail(index: number) {
    this.router.navigate(['/catalog/' + this.selectedBookCategoryId +  '/book/' + this.books[index].bookId]);
  }

  notifyMe(index: number) {
    let bookId = this.books[index].bookId
    this.bookService.notifyMe(this.loggedInUserId, bookId).subscribe((isSuccess) => {
      if (isSuccess) {
        this.successMessage = "You will be notified, set back and take rest!"
      }
    }, (notifyError) => {
      this.errorMessage = notifyError
    })
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }



}
