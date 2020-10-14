import { Component, OnInit } from '@angular/core';
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
  selector: 'app-admin-add-book',
  templateUrl: './admin-add-book.component.html',
  styleUrls: ['./admin-add-book.component.css']
})
export class AdminAddBookComponent implements OnInit {

  errorMessage: string;
  successMessage: string;

  bookFormGroup: FormGroup;
  authors: BookAuthor[] = [];
  authorsName : string[] = [];
  filteredAuthors: Observable<string[]>;

  publishers: BookPublisher[] = [];
  publishersName : string[] = [];
  filteredPublishers: Observable<string[]>;

  categories: BookCategory[] = [];
  constructor(private _formBuilder: FormBuilder, private bookService: LibraryBookService) { 
    this.bookFormGroup = this._formBuilder.group({
      bookTitleCtrl: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]*$')]],
      bookCategoryCtrl: ['', [Validators.required]],
      bookAuthorCtrl: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]*$')]],
      bookPublisherCtrl: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]*$')]],
      bookDescriptionCtrl: ['', [Validators.required]],
      bookCopiesCtrl: ['', [Validators.required, Validators.min(1), Validators.max(1000), Validators.pattern('^[0-9]+$')]],
      bookCostCtrl: ['', [Validators.required, Validators.min(1), Validators.max(10000), Validators.pattern('^[0-9]+$')]],
      bookEditionCtrl: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]*$')]],
      bookImagesCtrl: ['', [Validators.required]]
    });


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
  
    let filteredAuthorsName =  this.authorsName.filter(author => {
      return author.toLowerCase().includes(filterValue.toLowerCase())
    });
    return filteredAuthorsName;
  }

  private _filterPublishers(publisherName: string): string[] {
    const filterValue = publisherName.toLowerCase();

    let filteredPublishersName =  this.publishersName.filter(publisher => {
      return publisher.toLowerCase().includes(filterValue)
    })
    return filteredPublishersName;
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

    let bookImages : string[]= []
    bookImages.push(this.bookImages.value)
    let book = new Book(this.bookTitle.value, this.bookCategory.value, bookAuthor, bookPublisher, this.bookDescription.value, this.bookCopies.value, this.bookCost.value, this.bookEdition.value, bookImages, true);
    book.bookAvailableCopies = this.bookCopies.value
    let books: Book[] = []
    books.push(book)
    this.bookService.addBook(books).subscribe((isSuccess) => {
      this.successMessage = "Book Added successfully"
    },
      (bookAdditionError) => {
        this.errorMessage = bookAdditionError
      })
  }


  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }
}
