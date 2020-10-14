import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { Book } from '../model/book';
import { MatPaginator, MatSort, MatDialog } from '@angular/material';
import { LibraryBookService } from '../book-service';
import { FormBuilder } from '@angular/forms';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { HttpParams } from '@angular/common/http';
import { DetailModifyComponent } from '../detail-modify/detail-modify.component';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit, AfterViewInit {

  displayedColumns : string[] = ["bookId", "bookTitle", "bookCategory","bookCopies" ,"bookAvailableCopies", "bookCost", "isAvailable", "action"]
  
  data: Book[] = [];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  errorMessage : string;
  successMessage : string;
  

  @ViewChild(MatPaginator,{static:false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static:false}) sort: MatSort;

  constructor(private bookService: LibraryBookService, private matDialog : MatDialog, private _formBuilder : FormBuilder) {}
  
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
          let params = new HttpParams()
          .append('pageNo',this.paginator.pageIndex.toString())
          .append('pageSize','5')
          .append('sortByColumn',this.sort.active)
          .append('sortDirection',this.sort.direction)
          return this.bookService.viewBooks
          (0, params);
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

  updateBookDetail(book : Book) {
    let dialogRef = this.matDialog.open(DetailModifyComponent, {
        data: book,
        position : {top : '0px'},
        height : '100%',
        width: '70%'
      
    });
  }

  changeBookStatus(book: Book) {
    book.isAvailable = !book.isAvailable
    this.bookService.updateBook(book).subscribe((updatedBook) => {
       if(updatedBook.isAvailable) {
         this.successMessage = "Book is available for patrons' borrow"
       } else {
         this.successMessage = "Book is unavailable for patrons' borrow"
       }
    }, (updationError) => {
      this.errorMessage = updationError;
    })
  }

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }

}
