import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { LibraryAuthService } from '../auth-service';

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

  constructor( private authService: LibraryAuthService, private router: Router, private _formBuilder: FormBuilder) { }

  ngOnInit() {

    this.loginFormGroup = this._formBuilder.group({
      emailCtrl: ['', [Validators.email, Validators.required]],
      passwordCtrl : ['', [Validators.required]]
    });
  }

  loginUser() {
   
    this.authService.loginUser(this.emailCtrl.value, this.passwordCtrl.value).subscribe(employee => {
      this.authService.loggedInUser = employee;
      this.authService.loggedIn.next(true);
      if(employee.isAdmin) {
        this.router.navigate(['./adminhomepage'])
      } else {
        this.router.navigate(['./userhomepage/book-detail'])
      }
      
    }, (loginError) => { 
      this.errorMessage = loginError.error.message; 

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
