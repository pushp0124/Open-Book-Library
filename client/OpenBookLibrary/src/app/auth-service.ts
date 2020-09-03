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

@Injectable({
  providedIn: 'root'
})
export class LibraryAuthService {

  loggedInUser : User = new User(1,"pushpagarwal@gmail.com","Push",null,null,false,"1234");
  loggedIn = new BehaviorSubject<boolean>(false);
  
  private baseUrl = "http://localhost:8100/auth";
  
  @Output() fireIsLoggedIn: EventEmitter<User> = new EventEmitter<User>();

  
  	constructor(private http: HttpClient, private router: Router) {}

  	get isLoggedIn() {
    return this.loggedIn.asObservable();
  	}
  	registerUser(user: User,password: string) : Observable<number>   {
		return this.http.post<number>(this.baseUrl + "/register/user/" + password,user);
	}
	
	loginUser(email: string, password: string) : Observable<User>  {
		return this.http.post<User>(this.baseUrl + "/login/" + email + "/" + password,null);
	}

	changePassword(phoneNo : string,newPassword : string) : Observable<User> {
		return this.http.get<User>(this.baseUrl + "/change/password/" + phoneNo + "/" + newPassword);
	}
	
	

  	logout() {
	  this.loggedIn.next(false);
	  this.loggedInUser = undefined;
	  this.router.navigate(['']);
  	}

}
