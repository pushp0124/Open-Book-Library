
import { IssueBook } from '../model/issueBook';
import { Component, ViewChild, AfterViewInit, OnInit, ViewEncapsulation } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';

import { LibraryBookService } from '../book-service';
import { IssueBookApi } from '../model/issueBookApi';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-deposit-book',
  templateUrl: './deposit-book.component.html',
  styleUrls: ['./deposit-book.component.css']
})
export class DepositBookComponent implements OnInit, AfterViewInit {


  data: IssueBook[] = [];

  dataSource : MatTableDataSource<IssueBook>;
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;


  selection = new SelectionModel<IssueBook>(true, []);

  constructor(private bookService : LibraryBookService) { }

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
           return this.bookService.viewAllIssuedBooks
             (this.paginator.pageIndex, 5, this.sort.active, this.sort.direction);
         }),
         map(data => {
           // Flip flag to show that loading has finished.
           this.isLoadingResults = false;
           this.isRateLimitReached = false;
           this.resultsLength = data.total_count;
          //  this.dataSource = new MatTableDataSource(data);
           return data.books;
         }),
         catchError(() => {
           this.isLoadingResults = false;
           this.isRateLimitReached = true;
           return observableOf([]);
         })
       ).subscribe(data => this.data = data);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }


  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
        this.selection.clear() :
        this.dataSource.data.forEach(row => this.selection.select(row));
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: IssueBook): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.issueId + 1}`;
  }

}
