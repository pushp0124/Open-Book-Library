import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';
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
  isUerLoggedIn$: Observable<boolean>; 

  options : string[] = ["All","Book Title", "Author", "Category","Publication"];

  searchPlaceholder  = "Search Books in All"

  selectedOption : number = 0;

  searchModel : SearchModel;

  isAdmin = false;

  constructor(private authService : LibraryAuthService, private router : Router,private bookService : LibraryBookService) { }

  ngOnInit() {
    this.isLoggedIn$ = this.authService.isLoggedIn;
    this.isUerLoggedIn$ = this.authService.isUserLoggedIn;
    
  }

  onLogout(){
    this.authService.logout();              
  }

  showProfile() {
    
    let authResponse = this.authService.getAuthResponse()
       authResponse.loggedInUserRoles.forEach(role => {
          if(role.name == 'ROLE_ADMIN') {
            this.isAdmin = true
            
          }
      });
    if(this.isAdmin) {
      this.router.navigate(['/adminhomepage/profile'])
    } else {
      this.router.navigate(['/catalog/-1/profile'])
    }
  }

  

  selectOption(selectedIndex : number) {
    this.selectedOption = selectedIndex;
    this.searchPlaceholder = "Search Books in " + this.options[selectedIndex]

  }

  searchFormSubmitted(searchText : string) {
    searchText = searchText.trim();
    let currentUrlSplit = this.router.url.toLowerCase().split("/")
    let indexOfCatalogString = currentUrlSplit.indexOf("catalog")
    let navigationCategoryId = indexOfCatalogString != -1 ? currentUrlSplit[indexOfCatalogString + 1] : 0  
    this.searchModel = new SearchModel();
    this.searchModel.searchText = undefined;
    this.searchModel.selectedOption = -1;
    if(searchText != undefined && searchText != null && searchText.length > 0) {
      this.router.navigate(['/catalog/' + navigationCategoryId])
      this.searchModel.searchText = searchText
      this.searchModel.selectedOption = this.selectedOption;
     
      this.bookService.changeSearch(this.searchModel);
    } else {
      this.router.navigate(['/catalog/' + navigationCategoryId])
      this.bookService.changeSearch(this.searchModel);
    }
  }

}
