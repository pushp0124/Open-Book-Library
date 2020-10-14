import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, AbstractControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material';
import { BookCategory } from '../model/bookCategory';
import { LibraryBookService } from '../book-service';

@Component({
  selector: 'app-category-modify',
  templateUrl: './category-modify.component.html',
  styleUrls: ['./category-modify.component.css']
})
export class CategoryModifyComponent implements OnInit {

  categoryFormGroup : FormGroup;

  errorMessage : string;
  successMessage : string;

  constructor(@Inject(MAT_DIALOG_DATA) public data : BookCategory, private _formBuilder : FormBuilder, private bookService : LibraryBookService) { }

  ngOnInit() {
    this.categoryFormGroup = this._formBuilder.group({
      categoryIdCtrl: [this.data.categoryId,[Validators.required]],
      categoryCtrl : [this.data.category, [Validators.required]]
    });
    this.categoryIdCtrl.disable();
  }


  get categoryIdCtrl(): AbstractControl {
    return this.categoryFormGroup.controls['categoryIdCtrl'];
  }

  get categoryCtrl(): AbstractControl {
    return this.categoryFormGroup.controls['categoryCtrl'];
  }

 updateCategory() {
   this.data.category = this.categoryCtrl.value;
   this.bookService.updateBookCategory(this.data).subscribe((updatedCategory) => {
       this.successMessage = "Category Updated successfully"
        
   },(updateCategoryError) => {
        this.errorMessage = updateCategoryError
   })
 }

 errorClosed() {
  this.errorMessage = undefined;
}

successClosed() {
  this.successMessage = undefined;
}



 
}
