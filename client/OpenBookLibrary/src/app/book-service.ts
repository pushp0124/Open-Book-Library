import { Injectable, Output, EventEmitter } from '@angular/core';
import { User } from './model/user';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Book } from './model/book';
import { BookCategory } from './model/bookCategory';
import { BookAuthor } from './model/bookAuthor';
import { BookPublisher } from './model/bookPublisher';
import { IssueBook } from './model/issueBook';
import { LibraryAuthService } from './auth-service';
@Injectable({
    providedIn: 'root'
})
export class LibraryBookService {


    private baseUrl = "http://localhost:8101/books";
 
    dateFormatter = 'yyyy-MM-dd'; 

    constructor(private http: HttpClient, private router: Router, private authService : LibraryAuthService) { }

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
        return this.http.post<IssueBook>(this.baseUrl + "/issueBook/" + issuedBookId, null,{headers:headers});
    }

}