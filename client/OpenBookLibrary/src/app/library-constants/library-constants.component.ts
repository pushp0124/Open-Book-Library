import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder, AbstractControl } from '@angular/forms';
import { LibraryAuthService } from '../auth-service';
import { LibraryConstants } from '../model/libraryConstants';

@Component({
  selector: 'app-library-constants',
  templateUrl: './library-constants.component.html',
  styleUrls: ['./library-constants.component.css']
})
export class LibraryConstantsComponent implements OnInit {

  constantsFormGroup : FormGroup;

  constants : LibraryConstants;

  isSettingsEditable = false;

  settingsButtonText = "Edit";

  errorMessage = undefined
  successMessage = undefined
  dataLoaded = false;

  constructor(private _formBuilder : FormBuilder, private authService : LibraryAuthService) { }

  ngOnInit() {

    this.authService.getLibraryConstants().subscribe((constants) => {
      this.constants = constants
      this.constantsFormGroup = this._formBuilder.group({
        lateFineCtrl : [constants.lateDepositFinePerDay, [Validators.required, Validators.min(0), Validators.max(1000), Validators.pattern('^[0-9]+$')]],
        maxBooksLimitCtrl : [constants.maximumBooksLimit, [Validators.required, Validators.min(1), Validators.max(10), Validators.pattern('^[0-9]+$')]],
        borrowBookDaysCtrl : [constants.bookBorrowDays, [Validators.required, Validators.min(1), Validators.max(60), Validators.pattern('^[0-9]+$')]]
      });
      this.dataLoaded = true
      this.constantsFormGroup.disable() 

    }, (error) => {
      this.errorMessage = error
      this.dataLoaded = false
    })

  }

  updateConstants() {
    this.constants.bookBorrowDays = this.borrowBookDaysCtrl.value
    this.constants.lateDepositFinePerDay = this.lateFineCtrl.value
    this.constants.maximumBooksLimit = this.maxBooksLimitCtrl.value
    
    this.authService.updateLibraryConstants(this.constants).subscribe((isSuccess) => {
        this.successMessage = "Settings updated successfully"
    }, (error) => {
      this.errorMessage = error
    })
  }


  toggleEdit() {
    this.isSettingsEditable = !this.isSettingsEditable
    if (this.isSettingsEditable) {
      this.settingsButtonText = "Cancel"
      this.constantsFormGroup.enable()
    } else {
      this.settingsButtonText = "Edit"
      this.constantsFormGroup.disable()
    }
  }

  get lateFineCtrl(): AbstractControl {
    return this.constantsFormGroup.controls['lateFineCtrl'];
  }

  get maxBooksLimitCtrl(): AbstractControl {
    return this.constantsFormGroup.controls['maxBooksLimitCtrl'];
  }

  get borrowBookDaysCtrl(): AbstractControl {
    return this.constantsFormGroup.controls['borrowBookDaysCtrl'];
  }

  

  
  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }

}
