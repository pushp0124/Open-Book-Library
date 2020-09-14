import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { BookAuthor } from '../model/bookAuthor';
import { BookPublisher } from '../model/bookPublisher';
import { LibraryBookService } from '../book-service';
import { Book } from '../model/book';
import { BookCategory } from '../model/bookCategory';

@Component({
  selector: 'app-admin-add-book',
  templateUrl: './admin-add-book.component.html',
  styleUrls: ['./admin-add-book.component.css']
})
export class AdminAddBookComponent implements OnInit {

  errorMessage : string;
  successMessage : string;

  bookFormGroup: FormGroup;
  authors: BookAuthor[] = [];
  filteredAuthors: Observable<BookAuthor[]>;

  publishers: BookPublisher[] = [];
  filteredPublishers: Observable<BookPublisher[]>;

  categories : BookCategory[] = [];
  constructor(private _formBuilder: FormBuilder, private bookService: LibraryBookService) { }

  ngOnInit() {

    this.bookService.getAllBookCategories(0,10000,"category","asc").subscribe((categoryApi) => {
      this.categories = categoryApi.categories;
    },(categoriesError) => {
      this.errorMessage = categoriesError.error.message;
    })

    this.bookService.getAllBookAuthors(0,10000,"category","asc").subscribe((authorApi) => {
      this.authors = authorApi.authors;
    },(authorsError) => {
      this.errorMessage = authorsError.error.message;
    })

    this.bookService.getAllBookPublishers(0,10000,"category","asc").subscribe((publisherApi) => {
      this.publishers = publisherApi.publishers;
    },(publishersError) => {
      this.errorMessage = publishersError.error.message;
    })

    this.bookFormGroup = this._formBuilder.group({
      bookTitleCtrl: ['', [Validators.required]],
      bookCategoryCtrl: ['', [Validators.required]],
      bookAuthorCtrl : ['', [Validators.required]],
      bookPublisherCtrl : ['',[Validators.required]],
      bookDescriptionCtrl : ['', [Validators.required]],
      bookCopiesCtrl : ['', [Validators.required]],
      bookCostCtrl : ['', [Validators.required]],
      bookEditionCtrl : ['', [Validators.required]],
      bookImagesCtrl : ['', [Validators.required]]
    });
    this.filteredAuthors = this.bookAuthor.valueChanges
    .pipe(
      startWith(''),
      map(value => this._filterAuthors(value))
    );
    this.filteredPublishers = this.bookPublisher.valueChanges
    .pipe(
      startWith(''),
      map(value => this._filterPublishers(value))
    );
  }

  private _filterAuthors(author: BookAuthor): BookAuthor[] {
    const filterValue = author.authorName.toLowerCase();

    return this.authors.filter(author => author.authorName.toLowerCase().includes(filterValue));
  }

  private _filterPublishers(publisher: BookPublisher): BookPublisher[] {
    const filterValue = publisher.publisherName.toLowerCase();

    return this.publishers.filter(publisher => publisher.publisherName.toLowerCase().includes(filterValue));
  }

  //getting form-controls
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

  addBook() {
    let book = new Book(this.bookTitle.value,this.bookCategory.value,this.bookAuthor.value,this.bookPublisher.value,this.bookDescription.value,this.bookCopies.value,this.bookCost.value,this.bookEdition.value,this.bookImages.value);
    this.bookService.addBook(book).subscribe((book) => {
      this.successMessage = "Book Added successfully"
    },
    (bookAdditionError) => {
      this.errorMessage = bookAdditionError.error.message;
    })
  }


  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }
}
