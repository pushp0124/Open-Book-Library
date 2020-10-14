import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { BookCategory } from '../model/bookCategory';
import { LibraryBookService } from '../book-service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { HttpParams } from '@angular/common/http';
import { MatDialog } from '@angular/material';
import { CategoryModifyComponent } from '../category-modify/category-modify.component';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';

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

  categoryFormGroup : FormGroup;

  errorMessage : string;
  successMessage : string;
  

  @ViewChild(MatPaginator,{static:false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static:false}) sort: MatSort;

  constructor(private bookService: LibraryBookService, private matDialog : MatDialog, private _formBuilder : FormBuilder) {}
  
  ngOnInit() {
    this.categoryFormGroup = this._formBuilder.group({
      categoryCtrl : ['', [Validators.required, Validators.pattern('^[a-zA-Z ]*$')]]
    });

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
          return this.bookService.getAllBookCategories
          (params);
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.total_count;

          return data.categories;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => this.data = data);
  }
  updateBookCategory(category : BookCategory) {
    let dialogRef = this.matDialog.open(CategoryModifyComponent, {
      data: category
    });
  }

  get categoryCtrl(): AbstractControl {
    return this.categoryFormGroup.controls['categoryCtrl'];
  }


  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }


  addCategory() {
    let category = new BookCategory();
    category.category = this.categoryCtrl.value;
    this.bookService.addBookCategory(category).subscribe((category) => {
        this.successMessage = "Category Added Successfully";
    },(publisherError) => {
      this.errorMessage = publisherError.error.message;
    })
  }

}
