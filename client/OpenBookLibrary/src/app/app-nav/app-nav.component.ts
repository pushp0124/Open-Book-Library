import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { LibraryAuthService } from '../auth-service';
import { NgForm } from '@angular/forms';
import { LibraryBookService } from '../book-service';
import { SearchModel } from '../model/searchModel';

@Component({
  selector: 'app-nav',
  templateUrl: './app-nav.component.html',
  styleUrls: ['./app-nav.component.css']
})
export class AppNavComponent implements OnInit {

  isLoggedIn$: Observable<boolean>; 

  options : string[] = ["All","Book Title", "Author", "Category","Publication"];

  searchPlaceholder  = "Search Books in All"

  selectedOption : number = 0;

  searchModel : SearchModel;

  constructor(private authService : LibraryAuthService, private router : Router,private bookService : LibraryBookService) { }

  ngOnInit() {
    this.isLoggedIn$ = this.authService.isLoggedIn;

  }

  onLogout(){
    this.authService.logout();              
  }

  showProfile() {
    if(this.authService.loggedInUser.isAdmin) {
      this.router.navigate(['/adminhomepage/profile'])
    } else {
      this.router.navigate(['/userhomepage/profile'])
    }
  }

  selectOption(selectedIndex : number) {
    this.selectedOption = selectedIndex;
    this.searchPlaceholder = "Search Books in " + this.options[selectedIndex]

  }

  searchFormSubmitted(searchText : string) {
    searchText = searchText.trim();
    this.searchModel = new SearchModel();
    this.searchModel.searchText = undefined;
    this.searchModel.selectedOption = -1;
    if(searchText != undefined && searchText != null && searchText.length > 0) {
      this.router.navigate(['/userhomepage'])
     
      this.searchModel.searchText = searchText
      this.searchModel.selectedOption = this.selectedOption;
     
      this.bookService.changeSearch(this.searchModel);
    } else {
      this.router.navigate(['/userhomepage'])
      this.bookService.changeSearch(this.searchModel);
    }
  }

}
