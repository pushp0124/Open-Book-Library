import { Component, OnInit, ViewChild } from '@angular/core';
import { Label, MultiDataSet, SingleDataSet } from 'ng2-charts';
import { ChartType, ChartDataSets, ChartOptions } from 'chart.js';
import * as pluginDataLabels from 'chartjs-plugin-datalabels';
import { LibraryBookService } from '../book-service';
import { InventoryReport } from '../model/inventoryReport';
import { Router } from '@angular/router';
import { HttpParams } from '@angular/common/http';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { DatePipe } from '@angular/common';
import { BookTransaction } from '../model/bookTransaction';
import { TopBorrowedBooks } from '../model/topBorrowedBooks';


@Component({
  selector: 'app-admin-home-child',
  templateUrl: './admin-home-child.component.html',
  styleUrls: ['./admin-home-child.component.css']
})
export class AdminHomeChildComponent implements OnInit {

  inventoryReport : InventoryReport;
  errorMessage : string;
  successMessage : string;

  topBorrowedBooksData : TopBorrowedBooks[];

  displayedColumns: string[] = ["borrowedBook.bookTitle", "borrowedToUser.userEmail", "borrowedDate", "returnDate", "borrowedToUser.userPhoneNo"]

  firstData: BookTransaction[] = [];

  secondData :BookTransaction[] = [];

  resultsLength1 = 0;
  isLoadingResults1 = true;
  isRateLimitReached1 = false;

  resultsLength2 = 0;
  isLoadingResults2 = true;
  isRateLimitReached2 = false;

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;

  public barChartOptions: ChartOptions = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: {
      xAxes: [{
        display : false,
        ticks : {
          display : false
        }
      }], yAxes: [{
        ticks: {
          beginAtZero: true,
          callback: function (value: number) { if (value % 1 === 0) { return value; } }
        }
      }]
    },
    plugins: {
      datalabels: {
        anchor: 'end',
        align: 'end',
        formatter: function (value, context) {
          if (value == 0)
            return "";
        }
      }
    },
    legend : {
      position : "bottom"
    }

  };

  text  = "text"
  public doughnutChartOptions : ChartOptions = {
    maintainAspectRatio: false
  }
  public barChartLabels: Label[] = [];
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;
  public barChartPlugins = [pluginDataLabels];

  public chartColors: any[] = [
    { 
      backgroundColor:"#4169e1"
    }];

  public barChartData: ChartDataSets[] = [
    { data: [], label: 'Book Borrows' },
    
  ];


  public doughnutChartLabels: Label[] = ['Total Books', 'Lost Books', 'Borrowed Books'];
  public doughnutChartData: SingleDataSet = [];
  public doughnutChartType: ChartType = 'doughnut';

  public doughnutChartColors: any[] = [
    {backgroundColor:["#9E120E","#FF5800","#FFB414"]}
  ];


  constructor(private bookService : LibraryBookService, private router : Router, private datePipe : DatePipe) { }

  ngOnInit() {
    this.bookService.getInventoryReport().subscribe((report) => {
        this.inventoryReport = report
        this.doughnutChartData.push(this.inventoryReport.totalBooks)
        this.doughnutChartData.push(this.inventoryReport.totalBooksLost)
        this.doughnutChartData.push(this.inventoryReport.borrowedBooks)
    }, (reportError) => {
      this.errorMessage = reportError;
    })

    let params = new HttpParams().append('limit', '5')
    this.bookService.getTopBorrowedBooks(params).subscribe((topBorrowedBooks) => {
        console.log(topBorrowedBooks)
        this.topBorrowedBooksData = topBorrowedBooks;
        this.topBorrowedBooksData.forEach((issuedBookReport) => {
            this.barChartData[0].data.push(issuedBookReport.borrowCount)
            this.barChartLabels.push(issuedBookReport.borrowedBook.bookTitle)
        })
        console.log(this.barChartData);
        
    }, (topError) => {
      this.errorMessage = topError;
    })
  }
 

  ngAfterViewInit() {

    let currentDate = new Date();  
    let transformedDate = this.datePipe.transform(currentDate, this.bookService.dateFormatter);
    let transformedTomorrowDate = this.datePipe.transform(new Date(currentDate.getDate() + 1), this.bookService.dateFormatter);
    
    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults1 = true;
          let params = new HttpParams()
          .append('pageNo',this.paginator.pageIndex.toString())
          .append('pageSize','5')
          .append('sortByColumn',this.sort.active)
          .append('sortDirection',this.sort.direction)
          .append('returnDate',transformedDate)
          return this.bookService.viewAllBorrowedBooks
            (params);
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults1 = false;
          this.isRateLimitReached1 = false;
          this.resultsLength1 = data.total_count;

          return data.books;
        }),
        catchError(() => {
          this.isLoadingResults1 = false;
          this.isRateLimitReached1 = true;
          return observableOf([]);
        })
      ).subscribe(data => this.firstData = data);



      merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults2 = true;
          let params = new HttpParams()
          .append('pageNo',this.paginator.pageIndex.toString())
          .append('pageSize','5')
          .append('sortByColumn',this.sort.active)
          .append('sortDirection',this.sort.direction)
          .append('returnDate',transformedTomorrowDate)
          return this.bookService.viewAllBorrowedBooks
            (params);
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults2 = false;
          this.isRateLimitReached2 = false;
          this.resultsLength2 = data.total_count;

          return data.books;
        }),
        catchError(() => {
          this.isLoadingResults2 = false;
          this.isRateLimitReached2 = true;
          return observableOf([]);
        })
      ).subscribe(data => this.secondData = data);
  }


  onCardClicked(cardIndex : number) {
      if(cardIndex == 0) {
        this.router.navigate(['/adminhomepage/book-list'])
      } else if(cardIndex == 1) {
        this.router.navigate(['/adminhomepage/book-transactions'])
      } else {
        this.router.navigate(['/adminhomepage/user-book-report'])
      }
  }


  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }
 

}
