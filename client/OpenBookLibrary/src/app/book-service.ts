import { Injectable, Output, EventEmitter } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Book } from './model/book';
import { IssueBookApi } from './model/bookTransactionDto';
import { BookCategory } from './model/bookCategory';
import { BookAuthor } from './model/bookAuthor';
import { BookPublisher } from './model/bookPublisher';
import { BookTransaction } from './model/bookTransaction';
import { LibraryAuthService } from './auth-service';
import { SearchModel } from './model/searchModel';
import { BookCategoryApi } from './model/bookCategoryApi';
import { BookAuthorApi } from './model/bookAuthorApi';
import { BookPublisherApi } from './model/bookPublisherApi';
import { BookDto } from './model/bookDto';
import { InventoryReport } from './model/inventoryReport';
import { TopBorrowedBooks } from './model/topBorrowedBooks';

@Injectable({
    providedIn: 'root'
})


export class LibraryBookService {


    private baseUrl = "http://localhost:8100/books";

    dateFormatter = 'yyyy-MM-dd';

    private searchModel = new BehaviorSubject(new SearchModel());
    currentSearch$ = this.searchModel.asObservable();

    constructor(private http: HttpClient, private router: Router, private authService: LibraryAuthService) { }


    viewBooks(selectedCategoryId : number, params? : HttpParams): Observable<BookDto> {
        return this.http.get<BookDto>(this.baseUrl + "/view/all/books/" + selectedCategoryId, {params : params});

    }

    addBook(books: Book[]) : Observable<Boolean> {
        return this.http.post<Boolean>(this.baseUrl + "/add/books", books);
    }

    addBookCategory(category: BookCategory): Observable<BookCategory> {
        return this.http.post<BookCategory>(this.baseUrl + "/add/category", category);
    }

    addBookAuthor(author: BookAuthor): Observable<BookAuthor> {
        return this.http.post<BookAuthor>(this.baseUrl + "/add/author", author);
    }

    addBookPublisher(publisher: BookPublisher): Observable<BookPublisher> {
        return this.http.post<BookPublisher>(this.baseUrl + "/add/publisher", publisher);
    }

    updateBook(updatedBook: Book): Observable<Book> {
        return this.http.put<Book>(this.baseUrl + "/update/book", updatedBook);
    }

    updatePublisher(updatedPublisher : BookPublisher) : Observable<BookPublisher>{
        return this.http.put<BookPublisher>(this.baseUrl + "/update/publisher", updatedPublisher)
    }

    updateBookCategory(updatedCategory : BookCategory) : Observable<BookCategory>{
        return this.http.put<BookCategory>(this.baseUrl + "/update/category", updatedCategory)
    }

    updateBookAuthor(updatedAuthor : BookAuthor) : Observable<BookAuthor>{
        return this.http.put<BookAuthor>(this.baseUrl + "/update/author", updatedAuthor)
    }


    borrowBook(issuedBookId: number, borrowedToUser : number, borrowedDate : string): Observable<BookTransaction> {

       
        return this.http.post<BookTransaction>(this.baseUrl + "/borrowBook/" + issuedBookId + "/" + borrowedToUser + "/" + borrowedDate, null);
    }

    viewBorrowedBooks(issuedToUserId: number, params : HttpParams) : Observable<IssueBookApi> {
    
        return this.http.get<IssueBookApi>(this.baseUrl + "/borrowedBooks/" + issuedToUserId, {params : params});
    }
    

    viewAllBorrowedBooks(params : HttpParams) : Observable<IssueBookApi> {
        return this.http.get<IssueBookApi>(this.baseUrl + "/borrowedBooks", {params : params});
    }
    
	viewBooksByTitleSearch(selectedCategoryId : number, bookTitleSearchString : string) : Observable<Book[]> {
		return this.http.get<Book[]>(this.baseUrl + "/search/title/" + bookTitleSearchString + "/" + selectedCategoryId)
    }
    
    viewBooksByAuthorSearch(selectedCategoryId : number, bookAuthorSearchString : string) : Observable<Book[]> {
		return this.http.get<Book[]>(this.baseUrl + "/search/author/" + bookAuthorSearchString + "/" + selectedCategoryId)
    }
    

    viewBooksByCategorySearch(selectedCategoryId : number, bookCategorySearchString : string) : Observable<Book[]> {
		return this.http.get<Book[]>(this.baseUrl + "/search/category/" + bookCategorySearchString + "/" + selectedCategoryId)
    }
    

    viewBooksByPublisherSearch(selectedCategoryId : number, bookPublisherSearchString : string) : Observable<Book[]> {
		return this.http.get<Book[]>(this.baseUrl + "/search/publisher/" + bookPublisherSearchString + "/" + selectedCategoryId)
    }


    viewBooksByAllSearch(selectedCategoryId : number, bookAllSearchString : string) : Observable<Book[]> {
		return this.http.get<Book[]>(this.baseUrl + "/search/all/" + bookAllSearchString + "/" + selectedCategoryId)
    }

    getAllBookCategories(params : HttpParams) : Observable<BookCategoryApi> {
        return this.http.get<BookCategoryApi>(this.baseUrl + "/get/all/categories", {params : params});
    }

    getAllBookAuthors(params : HttpParams) : Observable<BookAuthorApi> {
        return this.http.get<BookAuthorApi>(this.baseUrl + "/get/all/authors", {params : params});
    }

    getAllBookPublishers(params : HttpParams) : Observable<BookPublisherApi> {
        return this.http.get<BookPublisherApi>(this.baseUrl + "/get/all/publishers", {params : params});
    }

    depositBorrowedBook(transactionId : number) : Observable<BookTransaction> {
        return this.http.put<BookTransaction>(this.baseUrl + "/depositBook/" + transactionId, null);
    }
    
    changeSearch(search: SearchModel) {
        this.searchModel.next(search)
    }

    bookLostByUser(issuedBookId : number) : Observable<BookTransaction[]> {
        return this.http.put<BookTransaction[]>(this.baseUrl + "/lostBook/" + issuedBookId, null);
    }

    getBorrowedBooksDate()  : Observable<Date[]>{
        return this.http.get<Date[]>(this.baseUrl + "/get/borrowedBooksDate");
    }

    getTopBorrowedBooks(params : HttpParams) : Observable<TopBorrowedBooks[]>{
        return this.http.get<TopBorrowedBooks[]>(this.baseUrl + "/get/topBorrowedBooks", {params : params})
    }

    getInventoryReport() : Observable<InventoryReport> {
        return this.http.get<InventoryReport>(this.baseUrl + "/get/inventoryReport")
    }

    notifyMe(userId : number, bookId : number) : Observable<Boolean> {
        return this.http.put<Boolean>(this.baseUrl + "/add/in-notification-list/" + userId + "/" +bookId, null)
    }

    getBookById(bookId : number)  : Observable <Book>{
        return this.http.get<Book>(this.baseUrl + "/get/bookDetail/" + bookId);
    }

    getBooksOfAuthor(authorId : number, params? : HttpParams) : Observable<BookDto> {
        return this.http.get<BookDto>(this.baseUrl + "/get/books/ofAuthor/" + authorId, {params : params});
    }
    
    getBooksOfCategory(categoryId : number, params? : HttpParams) {
        return this.http.get<BookDto>(this.baseUrl + "/get/books/ofCategory/" + categoryId, {params : params});
    }
}