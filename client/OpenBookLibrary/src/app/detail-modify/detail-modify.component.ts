import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { BookAuthor } from '../model/bookAuthor';
import { BookPublisher } from '../model/bookPublisher';
import { LibraryBookService } from '../book-service';
import { Book } from '../model/book';
import { BookCategory } from '../model/bookCategory';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-detail-modify',
  templateUrl: './detail-modify.component.html',
  styleUrls: ['./detail-modify.component.css']
})
export class DetailModifyComponent implements OnInit {



  errorMessage: string;
  successMessage: string;
  selectedCategory : BookCategory;
  category : BookCategory;
  bookFormGroup: FormGroup;
  authors: BookAuthor[] = [];
  authorsName: string[] = [];
  filteredAuthors: Observable<string[]>;

  publishers: BookPublisher[] = [];
  publishersName: string[] = [];
  filteredPublishers: Observable<string[]>;

  categories: BookCategory[] = [];
  constructor(@Inject(MAT_DIALOG_DATA) public data: Book, private _formBuilder: FormBuilder, private bookService: LibraryBookService) {


    this.bookFormGroup = this._formBuilder.group({
      bookIdCtrl: [data.bookId, [Validators.required]],
      bookTitleCtrl: [data.bookTitle, [Validators.required]],
      bookCategoryCtrl: [data.bookCategory.categoryId, [Validators.required]],
      bookAuthorCtrl: [data.bookAuthor.authorName, [Validators.required, Validators.pattern('^[a-zA-Z ]*$')]],
      bookPublisherCtrl: [data.bookPublisher.publisherName, [Validators.required, Validators.pattern('^[a-zA-Z ]*$')]],
      bookDescriptionCtrl: [data.bookDescription, [Validators.required]],
      bookCopiesCtrl: [data.bookCopies, [Validators.required, Validators.min(this.data.bookCopies - this.data.bookAvailableCopies), Validators.max(1000), Validators.pattern('^[0-9]+$')]],
      bookCostCtrl: [data.bookCost, [Validators.required, Validators.min(1), Validators.max(10000), Validators.pattern('^[0-9]+$')]],
      bookEditionCtrl: [data.bookEdition, [Validators.required]],
      bookImagesCtrl: [data.bookImages[0], [Validators.required]]
    });

    console.log(this.data);
    this.bookCategory.setValue(data.bookCategory.categoryId)
    this.bookId.disable();

  }


  compareCategoryObjects(object1: BookCategory, object2: BookCategory) {
    return object1 && object2 && object1.categoryId == object2.categoryId;
}
  ngOnInit() {


    let categoryParams = new HttpParams()
      .append('sortByColumn', 'category')
      .append('sortDirection', 'asc')

    let authorParams = new HttpParams()
      .append('sortByColumn', 'authorName')
      .append('sortDirection', 'asc')

    let publisherParams = new HttpParams()
      .append('sortByColumn', 'publisherName')
      .append('sortDirection', 'asc')

    this.bookService.getAllBookCategories(categoryParams).subscribe((categoryApi) => {
      this.categories = categoryApi.categories;
    }, (categoriesError) => {
      this.errorMessage = categoriesError.error.message;
    })

    this.bookService.getAllBookAuthors(authorParams).subscribe((authorApi) => {
      this.authors = authorApi.authors;
      this.authors.forEach(author => {
        this.authorsName.push(author.authorName);
      });
      this.filteredAuthors = this.bookAuthor.valueChanges
        .pipe(
          startWith(''),
          map(value => value ? this._filterAuthors(value) : this.authorsName)
        );
      console.log(this.filteredAuthors);
    }, (authorsError) => {
      this.errorMessage = authorsError.error.message;
    })

    this.bookService.getAllBookPublishers(publisherParams).subscribe((publisherApi) => {
      this.publishers = publisherApi.publishers;
      this.publishers.forEach(publisher => {
        this.publishersName.push(publisher.publisherName);
      });
      this.filteredPublishers = this.bookPublisher.valueChanges
        .pipe(
          startWith(''),
          map(value => this._filterPublishers(value))
        );
    }, (publishersError) => {
      this.errorMessage = publishersError.error.message;
    })




  }

  private _filterAuthors(authorName: string): string[] {

    const filterValue = authorName.toLowerCase();

    let filteredAuthorsName = this.authorsName.filter(author => {
      return author.toLowerCase().includes(filterValue.toLowerCase())
    });
    return filteredAuthorsName;
  }

  private _filterPublishers(publisherName: string): string[] {
    const filterValue = publisherName.toLowerCase();

    let filteredPublishersName = this.publishersName.filter(publisher => {
      return publisher.toLowerCase().includes(filterValue)
    })
    return filteredPublishersName;
  }

  //getting form-controls

  get bookId(): AbstractControl {
    return this.bookFormGroup.controls['bookIdCtrl'];
  }

  get bookTitle(): AbstractControl {
    return this.bookFormGroup.controls['bookTitleCtrl'];
  }
  get bookCategory(): AbstractControl {
    return this.bookFormGroup.controls['bookCategoryCtrl'];
  }

  get bookAuthor(): AbstractControl {
    return this.bookFormGroup.controls['bookAuthorCtrl'];
  }

  get bookPublisher(): AbstractControl {
    return this.bookFormGroup.controls['bookPublisherCtrl'];
  }
  get bookDescription(): AbstractControl {
    return this.bookFormGroup.controls['bookDescriptionCtrl'];
  }

  get bookCopies(): AbstractControl {
    return this.bookFormGroup.controls['bookCopiesCtrl'];
  }


  get bookEdition(): AbstractControl {
    return this.bookFormGroup.controls['bookEditionCtrl'];
  }

  get bookCost(): AbstractControl {
    return this.bookFormGroup.controls['bookCostCtrl'];
  }


  get bookImages(): AbstractControl {
    return this.bookFormGroup.controls['bookImagesCtrl'];
  }

  updateBook() {
    let bookAuthor = new BookAuthor();
    bookAuthor.authorName = this.bookAuthor.value
     this.authors.forEach((author) => {
          if(author.authorName == bookAuthor.authorName) {
              bookAuthor = author;
          } 
    })

    let bookPublisher = new BookPublisher();
    bookPublisher.publisherName = this.bookPublisher.value
     this.publishers.forEach((publisher) => {
          if(publisher.publisherName == bookAuthor.authorName) {
              bookPublisher = publisher;
          } 
    })

    let _bookCategory : BookCategory;
   
    this.categories.forEach((category) => {
          if(category.categoryId == this.bookCategory.value) {
            _bookCategory = category
          } 
    })

    let bookImages : string[]= []
    bookImages.push(this.bookImages.value)



    let book = new Book(this.bookTitle.value, _bookCategory, bookAuthor,bookPublisher, this.bookDescription.value, this.bookCopies.value, this.bookCost.value, this.bookEdition.value, bookImages, this.data.isAvailable);
    book.bookId = this.data.bookId

    this.bookService.updateBook(book).subscribe((isSuccess) => {
      this.successMessage = "Book Updated successfully"
    },
      (bookUpdationError) => {
        this.errorMessage = bookUpdationError;
      })
  }


  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }

}
