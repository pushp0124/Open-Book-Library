import { Component, OnInit } from '@angular/core';
import { Book } from '../model/book';
import { BookTransaction } from '../model/bookTransaction';
import { LibraryBookService } from '../book-service';
import { LibraryAuthService } from '../auth-service';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { BookDto } from '../model/bookDto';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  errorMessage: string;
  successMessage: string;
  loggedInUserId: number;
  book: Book;
  dataLoaded = false
  durationInSeconds = 5;

  authorBooks : Book[]

  categoryBooksDto : BookDto;

  stars: number[] = [1, 2, 3, 4, 5];
  selectedValue: number;

  customOptions: OwlOptions = {
    navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
    loop: true,
    autoplay: true,
    center: true,
    dots: true,
    nav : true,
    autoHeight: true,
    autoWidth: true,
    
  }

  constructor(private bookService: LibraryBookService, private authService: LibraryAuthService, private datePipe : DatePipe, private route : ActivatedRoute, private _snackBar: MatSnackBar, private router : Router) { }

  ngOnInit() {
    this.loggedInUserId = this.authService.getAuthResponse().loggedInUserId
    const _id = this.route.snapshot.paramMap.get('id');
    const id = isNaN(+_id) ? 0 : +_id
    this.getBook(+id);

  }

  getBook(bookId : number ) {
    this.bookService.getBookById(bookId).subscribe((book) => {
      this.book = book;
      this.getBooksOfAuthor()
    }, (error) => {
      this.dataLoaded = false
      this.errorMessage = error;
    })

  }
  borrowBook() {
    
    let transformedDate = this.datePipe.transform(new Date(), this.bookService.dateFormatter);
    this.bookService.borrowBook(this.book.bookId, this.loggedInUserId, transformedDate).subscribe((bookTransaction: BookTransaction) => {
      let returnDate = this.datePipe.transform(bookTransaction.returnDate, 'dd-MM-yyyy')
      this.successMessage = "Book Borrowed successfully, its return date is " + returnDate;
    }, (bookBorrowError) => {
      this.errorMessage = bookBorrowError
    })
  }

  notifyMe() {
   
    this.bookService.notifyMe(this.loggedInUserId, this.book.bookId).subscribe((isSuccess) => {
      if (isSuccess) {
          this.openSnackBar()
      }
    }, (notifyError) => {
      this.errorMessage = notifyError
    })
  }

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }

  openSnackBar() {
    this._snackBar.openFromComponent(NotifyAvailableComponent, {
      duration: this.durationInSeconds * 1000,
    });
  }

  viewBookDetail(bookId: number) {
    // this.bookService.detailAboutBook.borrowedBook = this.books[index];
    console.log("hello", bookId);
    
    this.router.navigate(['/catalog/0/book/' + bookId]);
  }

  getBooksOfAuthor() {
    this.bookService.getBooksOfAuthor(this.book.bookAuthor.bookAuthorId).subscribe((booksDto : BookDto) => {
          this.authorBooks = booksDto.books
          this.authorBooks = this.authorBooks.filter((authorBook) => {
            if(authorBook.bookId != this.book.bookId) {
              return authorBook;
            }
            console.log(this.authorBooks);
            this.getBooksOfCategory()
           
          }, (bookAuthorError) => {
            this.errorMessage = bookAuthorError
            this.dataLoaded = false
          })
    })
  }

  getBooksOfCategory() {
    let params = new HttpParams()
    .append('pageNo','0')
    .append('pageSize','5')
    this.bookService.getBooksOfCategory(this.book.bookCategory.categoryId, params).subscribe((booksDto : BookDto) => {
        this.categoryBooksDto = booksDto
        console.log(this.categoryBooksDto);
        
        this.dataLoaded = true
    }, (bookCategoryError) => {
      this.errorMessage = bookCategoryError
      this.dataLoaded = false
    })
  }

  seeAllBooksOfCategory() {
    this.router.navigate(['/catalog/' + this.book.bookCategory.categoryId])
  }

  countStar(star) {
    this.selectedValue = star;
    console.log('Value of star', star);
  }

}



@Component({
  selector: 'notify-available',
  template: `<span class="notify-available">
  You will be notified, set back and take rest!
  </span>`,
  styles: [`
    .notify-available {
      color: hotpink;
    }
  `],
})
export class NotifyAvailableComponent {}