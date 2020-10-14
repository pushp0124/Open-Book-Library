import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material';
import { BookPublisher } from '../model/bookPublisher';
import { LibraryBookService } from '../book-service';

@Component({
  selector: 'app-publisher-modify',
  templateUrl: './publisher-modify.component.html',
  styleUrls: ['./publisher-modify.component.css']
})
export class PublisherModifyComponent implements OnInit {

  
  publisherFormGroup : FormGroup;

  errorMessage : string;
  successMessage : string;

  constructor(@Inject(MAT_DIALOG_DATA) public data : BookPublisher, private _formBuilder : FormBuilder, private bookService : LibraryBookService) { }

  ngOnInit() {
    this.publisherFormGroup = this._formBuilder.group({
      publisherIdCtrl: [this.data.publisherId,[Validators.required]],
      publisherCtrl : [this.data.publisherName, [Validators.required]]
    });
    this.publisherIdCtrl.disable();
  }


  get publisherIdCtrl(): AbstractControl {
    return this.publisherFormGroup.controls['publisherIdCtrl'];
  }

  get publisherCtrl(): AbstractControl {
    return this.publisherFormGroup.controls['publisherCtrl'];
  }

 updatePublisher() {
   this.data.publisherName = this.publisherCtrl.value;
   this.bookService.updatePublisher(this.data).subscribe((updatedPublisher) => {
    this.successMessage = "Publisher Updated successfully" 
   },(updatePublisherError) => {
    this.errorMessage = updatePublisherError
   })
 }
 errorClosed() {
  this.errorMessage = undefined;
}

successClosed() {
  this.successMessage = undefined;
}

}
