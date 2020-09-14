import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { BookCategory } from '../model/bookCategory';
import { LibraryBookService } from '../book-service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-add-book-category',
  templateUrl: './add-book-category.component.html',
  styleUrls: ['./add-book-category.component.css']
})
export class AddBookCategoryComponent implements OnInit, AfterViewInit {

  displayedColumns : string[] = ["categoryId", "category", "action"]
  
  data: BookCategory[] = [];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator,{static:false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static:false}) sort: MatSort;

  constructor(private bookService: LibraryBookService) {}
  
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
          return this.bookService.getAllBookCategories
          (this.paginator.pageIndex,10,this.sort.active,this.sort.direction);
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

}
