import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router, Route } from '@angular/router';
import { Observable } from 'rxjs';
import { LibraryAuthService } from './auth-service';
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: LibraryAuthService, private router: Router) { }
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let authReponse = this.authService.getAuthResponse()
    if (next.data.roles) {
      if (authReponse == undefined) {
        this.router.navigate(['/userlogin']);
        return false;
      } else {
        // user is logged in
        this.authService.loggedIn.next(true)
        //check if he has permission
        let roles = authReponse.loggedInUserRoles
        let toNavigate = false;
        let isUser : Boolean = undefined
        roles.forEach((role) => {
          let _roles: string[] = next.data.roles
          if (_roles.indexOf(role.name) != -1) {
            toNavigate = true
          }
          if(role.name == 'ROLE_USER') {
            isUser = true
          } else if(role.name == 'ROLE_ADMIN') {
            isUser = false
          }
        })
       
        if(isUser != undefined && isUser) {
          if(!toNavigate) {
            this.router.navigate(['/catalog/0'])
          }
          this.authService.isUserLoggedIn.next(true)
         
        } else if(isUser != undefined && !isUser) {
          if(!toNavigate) {
            this.router.navigate(['/adminhomepage'])
          }
          this.authService.isUserLoggedIn.next(false)
         
        }
        
        return toNavigate
      }
      
    } else {
      console.log("No data ")
      if(authReponse == undefined) {
        return true;
      } else {
        this.authService.loggedIn.next(true)
        //check if he has permission
        let roles = authReponse.loggedInUserRoles
      
        let isUser : Boolean = undefined;
        roles.forEach((role) => {
        
            if(role.name == 'ROLE_USER') {
              isUser = true;
            } else if(role.name == 'ROLE_ADMIN'){
              isUser = false;
            }
        })
        if(isUser != undefined && isUser) {
          this.authService.isUserLoggedIn.next(true)
          this.router.navigate(['/catalog/0'])
        } else if(isUser != undefined && !isUser) {
          this.authService.isUserLoggedIn.next(false)
          this.router.navigate(['/adminhomepage'])
        }
        return true
      }
    }
  }

}
