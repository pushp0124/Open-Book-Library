import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { LibraryAuthService } from '../auth-service';
import { AuthRequest } from '../model/authRequest';
import { User } from '../model/user';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {

  errorMessage : string;
  successMessage : string;
  
  loginFormGroup: FormGroup;

  hide = true;

  buttonTxt  = "Login"
  isLoggingIn  = false;
  constructor( private authService: LibraryAuthService, private router: Router, private _formBuilder: FormBuilder) { }

  ngOnInit() {

    this.loginFormGroup = this._formBuilder.group({
      emailCtrl: ['', [Validators.email, Validators.required]],
      passwordCtrl : ['', [Validators.required]]
    });
  }

  loginUser() {
   
    this.buttonTxt = "Logging you In ..."
    this.isLoggingIn = true

    let authRequest = new AuthRequest();
    authRequest.username = this.emailCtrl.value
    authRequest.password = this.passwordCtrl.value
    this.authService.loginUser(authRequest).subscribe(authResponse => {
     
      this.authService.loggedIn.next(true);
      this.authService.saveAuthResponse(authResponse);
      let isAdmin = false
       authResponse.loggedInUserRoles.forEach(role => {
          if(role.name == 'ROLE_ADMIN') {
            isAdmin = true
            
          }
      });
      if(isAdmin) {
        this.router.navigate(['./adminhomepage'])
      } else {
        this.router.navigate(['./catalog/0'])
      }
      
    }, (loginError) => { 
      this.buttonTxt = "Log In!"
      this.isLoggingIn = false;
      this.errorMessage = loginError; 

    }
    )
  }

  get emailCtrl(): AbstractControl {
    return this.loginFormGroup.controls['emailCtrl'];
  }

  get passwordCtrl(): AbstractControl {
    return this.loginFormGroup.controls['passwordCtrl'];
  }

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }
}
