import { Injectable, Output, EventEmitter } from '@angular/core';
import { User } from './model/user';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Book } from './model/book';
import { BookCategory } from './model/bookCategory';
import { BookAuthor } from './model/bookAuthor';
import { BookPublisher } from './model/bookPublisher';
import { IssueBook } from './model/issueBook';

@Injectable({
  providedIn: 'root'
})
export class OpenBookLibraryService {

  loggedInUser : User = new User(1,null,null,null,null,false);
  loggedIn = new BehaviorSubject<boolean>(false);
  dateFormatter = 'yyyy-MM-dd'; 

  private baseUrl = "http://localhost:8080";
  
  @Output() fireIsLoggedIn: EventEmitter<User> = new EventEmitter<User>();

  
  	constructor(private http: HttpClient, private router: Router) {}

  	get isLoggedIn() {
    return this.loggedIn.asObservable();
  	}
  	registerUser(user: User,password: string, isAdmin: boolean) : Observable<number>   {
		return this.http.post<number>(this.baseUrl + "/register/user/" + password + "/" + isAdmin,user);
	}
	
	loginUser(email: string, password: string) : Observable<User>  {
		return this.http.post<User>(this.baseUrl + "/login/" + email + "/" + password,null);
	}

	changePassword(phoneNo : string,newPassword : string) : Observable<User> {
		return this.http.get<User>(this.baseUrl + "/change/password/" + phoneNo + "/" + newPassword);
	}
	
	viewBooks() : Observable<Book[]> {
		return this.http.get<Book[]>(this.baseUrl + "/view/allBooks");
		
	}

	addBook(book : Book) : Observable<Book> {
		return this.http.post<Book>(this.baseUrl + "/add/book", book);
	}
	
	addBookCategory(category : BookCategory) : Observable<BookCategory> {
		return this.http.post<BookCategory>(this.baseUrl + "/add/category",category);
	}

	addBookAuthor(author : BookAuthor) : Observable<BookAuthor> {
		return this.http.post<BookAuthor>(this.baseUrl + "/add/author",author);
	}

	addBookPublisher(publisher : BookPublisher) : Observable<BookPublisher> {
		return this.http.post<BookPublisher>(this.baseUrl + "/add/publisher",publisher);
	}

	updateBook(updatedBook : Book) : Observable<Book> {
		return this.http.put<Book>(this.baseUrl + "/update/book",updatedBook);
	}

	issueBook(issuedDate : Date,returnDate : Date,issuedBookId : number,issuedToUserId : number) : Observable<IssueBook> {
		return this.http.post<IssueBook>(this.baseUrl + "/issueBook/ " + issuedDate + "/" + returnDate + "/" + issuedBookId + "/" + issuedToUserId, null);
	}

	

  	logout() {
	  this.loggedIn.next(false);
	  this.loggedInUser = undefined;
	  this.router.navigate(['']);
  	}

}
