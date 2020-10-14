import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { LibraryBookService } from '../book-service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { HttpParams } from '@angular/common/http';
import { BookAuthor } from '../model/bookAuthor';
import { MatDialog } from '@angular/material';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { AuthorModifyComponent } from '../author-modify/author-modify.component';


@Component({
  selector: 'app-add-book-author',
  templateUrl: './add-book-author.component.html',
  styleUrls: ['./add-book-author.component.css']
})
export class AddBookAuthorComponent implements OnInit, AfterViewInit {

  errorMessage : string;
  successMessage : string;
  
  displayedColumns : string[] = ["bookAuthorId", "authorName", "action"]
  
  data: BookAuthor[] = [];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  authorFormGroup : FormGroup;

  @ViewChild(MatPaginator,{static:false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static:false}) sort: MatSort;

  constructor(private _formBuilder : FormBuilder, private bookService: LibraryBookService, private matDialog: MatDialog) {}
  
  ngOnInit() {

    this.authorFormGroup = this._formBuilder.group({
     
      authorCtrl : ['', [Validators.required]]
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
          return this.bookService.getAllBookAuthors
          (params);
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.total_count;

          return data.authors;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => this.data = data);
  }

  updateBookAuthor(category : BookAuthor) {
    let dialogRef = this.matDialog.open(AuthorModifyComponent, {
      data: category
    });
  }

  get authorCtrl(): AbstractControl {
    return this.authorFormGroup.controls['authorCtrl'];
  }


  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }


  addAuthor() {
    let author = new BookAuthor();
    author.authorName = this.authorCtrl.value;
    this.bookService.addBookAuthor(author).subscribe((author) => {
        this.successMessage = "Author Added Succesffully";
    },(publisherError) => {
      this.errorMessage = publisherError.error.message;
    })
  }
}
