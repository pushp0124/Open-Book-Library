import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { LibraryAuthService } from '../auth-service';

@Component({
  selector: 'app-nav',
  templateUrl: './app-nav.component.html',
  styleUrls: ['./app-nav.component.css']
})
export class AppNavComponent implements OnInit {

  isLoggedIn$: Observable<boolean>; 
  constructor(private authService : LibraryAuthService, private router : Router) { }

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

}
