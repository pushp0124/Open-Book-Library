import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { OpenBookLibraryService } from '../book-store.service';

@Component({
  selector: 'app-nav',
  templateUrl: './app-nav.component.html',
  styleUrls: ['./app-nav.component.css']
})
export class AppNavComponent implements OnInit {

  isLoggedIn$: Observable<boolean>; 
  constructor(private OpenBookLibraryService : OpenBookLibraryService, private router : Router) { }

  ngOnInit() {
    this.isLoggedIn$ = this.OpenBookLibraryService.isLoggedIn;
  }

  onLogout(){
    this.OpenBookLibraryService.logout();              
  }

  showProfile() {
    if(this.OpenBookLibraryService.loggedInUser.isAdmin) {
      this.router.navigate(['/adminhomepage/profile'])
    } else {
      this.router.navigate(['/userhomepage/profile'])
    }
  }

}
