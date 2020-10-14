import { Injectable, Output, EventEmitter } from '@angular/core';
import { User } from './model/user';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { HttpClient, HttpParams } from '@angular/common/http';
import { AuthRequest } from './model/authRequest';
import { AuthResponse } from './model/authResponse';
import { LibraryConstants } from './model/libraryConstants';
import { ChangePasswordRequest } from './model/changePasswordRequest';
import { UpdateUserProfile } from './model/updateUserProfile';


const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';


@Injectable({
	providedIn: 'root'
})
export class LibraryAuthService {

	loggedIn = new BehaviorSubject<boolean>(false);
	isUserLoggedIn = new BehaviorSubject<boolean>(false);

	private baseUrl = "http://localhost:8100";

	@Output() fireIsLoggedIn: EventEmitter<User> = new EventEmitter<User>();


	constructor(private http: HttpClient, private router: Router) { }

	get isLoggedIn() {
		return this.loggedIn.asObservable();
	}
	registerUser(user: User): Observable<User> {
		return this.http.post<User>(this.baseUrl + "/register", user);
	}

	loginUser(authRequest: AuthRequest): Observable<AuthResponse> {
		return this.http.post<AuthResponse>(this.baseUrl + "/signin", authRequest);
	}

	changePassword(changePssswordRequest : ChangePasswordRequest): Observable<Boolean> {
		
		return this.http.put<Boolean>(this.baseUrl + "/changePassword", changePssswordRequest);
	}

	forgotPassword(mailId: string): Observable<Boolean> {
		return this.http.get<Boolean>(this.baseUrl + "/forgotPassword/" + mailId)
	}

	getUserProfile(userId: number) {
		return this.http.get<User>(this.baseUrl + "/get/user/" + userId)
	}

	public saveAuthResponse(authResponse: AuthResponse) {
		window.sessionStorage.removeItem(USER_KEY);
		window.sessionStorage.setItem(USER_KEY, JSON.stringify(authResponse));
	}

	public getAuthResponse(): AuthResponse {
		return JSON.parse(sessionStorage.getItem(USER_KEY));
	}

	viewAllPatrons(): Observable<User[]> {
		return this.http.get<User[]>(this.baseUrl + "/view/all/patrons");
	}

	logout() {
		this.loggedIn.next(false);
		window.sessionStorage.clear();
		this.router.navigate(['']);
	}

	disableUser(userId: number): Observable<Boolean> {
		return this.http.put<Boolean>(this.baseUrl + "/disableUser/" + userId, null);
	}

	getLibraryConstants(): Observable<LibraryConstants> {
		return this.http.get<LibraryConstants>(this.baseUrl + "/get/constants");
	}
	updateLibraryConstants(updatedConstants: LibraryConstants): Observable<Boolean> {
		return this.http.put<Boolean>(this.baseUrl + "/update/constants", updatedConstants);
	}

	updateUserProfile(updatedUserProfile : UpdateUserProfile): Observable<Boolean> {
		
		return this.http.put<Boolean>(this.baseUrl + "/update/userProfile", updatedUserProfile);
	}
}
