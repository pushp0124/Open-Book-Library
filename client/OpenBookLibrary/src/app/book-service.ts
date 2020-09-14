import { Injectable, Output, EventEmitter } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Book } from './model/book';
import { IssueBookApi } from './model/issueBookApi';
import { BookCategory } from './model/bookCategory';
import { BookAuthor } from './model/bookAuthor';
import { BookPublisher } from './model/bookPublisher';
import { IssueBook } from './model/issueBook';
import { LibraryAuthService } from './auth-service';
import { SearchModel } from './model/searchModel';
import { SortDirection } from '@angular/material/sort';
import { BookCategoryApi } from './model/bookCategoryApi';
import { BookAuthorApi } from './model/bookAuthorApi';
import { BookPublisherApi } from './model/bookPublisherApi';
@Injectable({
    providedIn: 'root'
})
export class LibraryBookService {


    private baseUrl = "http://localhost:8101/books";

    dateFormatter = 'yyyy-MM-dd';

    detailAboutBook = new IssueBook();

    private searchModel = new BehaviorSubject(new SearchModel());
    currentSearch$ = this.searchModel.asObservable();

    constructor(private http: HttpClient, private router: Router, private authService: LibraryAuthService) { }


    viewBooks(): Observable<Book[]> {
        return this.http.get<Book[]>(this.baseUrl + "/view/allBooks");

    }

    addBook(book: Book): Observable<Book> {
        return this.http.post<Book>(this.baseUrl + "/add/book", book);
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

    issueBook(issuedBookId: number): Observable<IssueBook> {

        let headers = new HttpHeaders().set('emailId', this.authService.loggedInUser.userEmail);
        headers = headers.set('password', "1234");
        console.log(headers)
        return this.http.post<IssueBook>(this.baseUrl + "/issueBook/" + issuedBookId, null, { headers: headers });
    }

    viewIssuedBooks(issuedToUserId: number, pageNumber: number, pageSize: number, sortByColumn : string, sortDirection : SortDirection) : Observable<IssueBookApi> {
        return this.http.get<IssueBookApi>(this.baseUrl + "/myBooks/" + issuedToUserId + "/" + pageNumber + "/" + pageSize + "/" + sortByColumn + "/" + sortDirection);
    }
    

    viewAllIssuedBooks(pageNumber: number, pageSize: number, sortByColumn : string, sortDirection : SortDirection) : Observable<IssueBookApi> {
        return this.http.get<IssueBookApi>(this.baseUrl + "/myBooks/" + pageNumber + "/" + pageSize + "/" + sortByColumn + "/" + sortDirection);
    }
    
	viewBooksByTitleSearch(bookTitleSearchString : string) : Observable<Book[]> {
		return this.http.get<Book[]>(this.baseUrl + "/search/title/" + bookTitleSearchString)
    }
    
    viewBooksByAuthorSearch(bookAuthorSearchString : string) : Observable<Book[]> {
		return this.http.get<Book[]>(this.baseUrl + "/search/author/" + bookAuthorSearchString)
    }
    

    viewBooksByCategorySearch(bookCategorySearchString : string) : Observable<Book[]> {
		return this.http.get<Book[]>(this.baseUrl + "/search/category/" + bookCategorySearchString)
    }
    

    viewBooksByPublisherSearch(bookPublisherSearchString : string) : Observable<Book[]> {
		return this.http.get<Book[]>(this.baseUrl + "/search/publisher/" + bookPublisherSearchString)
    }


    viewBooksByAllSearch(bookAllSearchString : string) : Observable<Book[]> {
		return this.http.get<Book[]>(this.baseUrl + "/search/all/" + bookAllSearchString)
    }

    getAllBookCategories(pageNumber: number, pageSize: number, sortByColumn : string, sortDirection : SortDirection) : Observable<BookCategoryApi> {
        return this.http.get<BookCategoryApi>(this.baseUrl + "/view/all/category/" + pageNumber + "/" + pageSize + "/" + sortByColumn + "/" + sortDirection);
    }

    getAllBookAuthors(pageNumber: number, pageSize: number, sortByColumn : string, sortDirection : SortDirection) : Observable<BookAuthorApi> {
        return this.http.get<BookAuthorApi>(this.baseUrl + "/view/all/author/" + pageNumber + "/" + pageSize + "/" + sortByColumn + "/" + sortDirection);
    }

    getAllBookPublishers(pageNumber: number, pageSize: number, sortByColumn : string, sortDirection : SortDirection) : Observable<BookPublisherApi> {
        return this.http.get<BookPublisherApi>(this.baseUrl + "/view/all/publisher" + pageNumber + "/" + pageSize + "/" + sortByColumn + "/" + sortDirection);
    }
    
    changeSearch(search: SearchModel) {
        this.searchModel.next(search)
    }
}