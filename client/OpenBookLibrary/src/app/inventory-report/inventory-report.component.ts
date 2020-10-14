import { Component, OnInit } from '@angular/core';
import { InventoryReport } from '../model/inventoryReport';
import { LibraryBookService } from '../book-service';

@Component({
  selector: 'app-inventory-report',
  templateUrl: './inventory-report.component.html',
  styleUrls: ['./inventory-report.component.css']
})
export class InventoryReportComponent implements OnInit {

  currentDate = new Date();

  libraryInventoryReport : InventoryReport;
  dataLoaded = false;

  errorMessage = undefined
  successMessage = undefined
  
  constructor(private bookService : LibraryBookService) { }

  ngOnInit() {
    this.bookService.getInventoryReport().subscribe((report) => {
      this.libraryInventoryReport = report;
      this.dataLoaded = true;
    }, (error) => {
      this.errorMessage = error;
    })
  }

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }
 


}
