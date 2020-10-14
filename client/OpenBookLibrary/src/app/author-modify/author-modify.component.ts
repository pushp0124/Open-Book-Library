import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material';
import { BookAuthor } from '../model/bookAuthor';
import { LibraryBookService } from '../book-service';

@Component({
  selector: 'app-author-modify',
  templateUrl: './author-modify.component.html',
  styleUrls: ['./author-modify.component.css']
})
export class AuthorModifyComponent implements OnInit {

  authorFormGroup: FormGroup;

  errorMessage : string;
  successMessage : string;
  constructor(@Inject(MAT_DIALOG_DATA) public data: BookAuthor, private _formBuilder: FormBuilder, private bookService: LibraryBookService) { }

  ngOnInit() {
    this.authorFormGroup = this._formBuilder.group({
      authorIdCtrl: [this.data.bookAuthorId, [Validators.required]],
      authorCtrl: [this.data.authorName, [Validators.required]]
    });
    this.authorIdCtrl.disable();
  }


  get authorIdCtrl(): AbstractControl {
    return this.authorFormGroup.controls['authorIdCtrl'];
  }

  get authorCtrl(): AbstractControl {
    return this.authorFormGroup.controls['authorCtrl'];
  }

  updateAuthor() {
    this.data.authorName = this.authorCtrl.value;
    this.bookService.updateBookAuthor(this.data).subscribe((updatedAuthor) => {
      this.successMessage = "Author Updated successfully"
    }, (updateAuthorError) => {
      this.errorMessage = updateAuthorError
    })
  }

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }

}
