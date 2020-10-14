import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { LibraryBookService } from '../book-service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { HttpParams } from '@angular/common/http';
import { BookPublisher } from '../model/bookPublisher';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { PublisherModifyComponent } from '../publisher-modify/publisher-modify.component';

@Component({
  selector: 'app-add-book-publisher',
  templateUrl: './add-book-publisher.component.html',
  styleUrls: ['./add-book-publisher.component.css']
})
export class AddBookPublisherComponent implements OnInit, AfterViewInit {

  errorMessage : string;
  successMessage : string;
  
  displayedColumns : string[] = ["publisherId", "publisherName", "action"]
  
  data: BookPublisher[] = [];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  publisherFormGroup : FormGroup;

  @ViewChild(MatPaginator,{static:false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static:false}) sort: MatSort;

  constructor(private _formBuilder : FormBuilder, private bookService: LibraryBookService, private matDialog: MatDialog) {}
  
  ngOnInit() {

    this.publisherFormGroup = this._formBuilder.group({
     
      publisherCtrl : ['', [Validators.required]]
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
          .append('pageSize','10')
          .append('sortByColumn',this.sort.active)
          .append('sortDirection',this.sort.direction)
          return this.bookService.getAllBookPublishers
          (params);
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.total_count;

          return data.publishers;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => this.data = data);
  }
  updateBookPublisher(publisher : BookPublisher) {
    let dialogRef = this.matDialog.open(PublisherModifyComponent, {
      data: publisher
    });
    
  }


  get publisherCtrl(): AbstractControl {
    return this.publisherFormGroup.controls['publisherCtrl'];
  }


  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }
  addPublisher() {

    let publisher = new BookPublisher();
    publisher.publisherName = this.publisherCtrl.value;
    this.bookService.addBookPublisher(publisher).subscribe((publisher) => {
        this.successMessage = "Publisher Added Succesffully";
    },(publisherError) => {
      this.errorMessage = publisherError.error.message;
    })
  }
 
}