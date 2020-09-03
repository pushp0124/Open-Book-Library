import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { LibraryAuthService } from '../auth-service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  
  constructor(private authService : LibraryAuthService, private _formBuilder: FormBuilder, private router: Router) { }

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup : FormGroup;
  isEditable = true;
  hide = true;
  isAdminSignUp = false;
  errorMessage : string;
  successMessage : string;

  
  ngOnInit() {
    
    this.firstFormGroup = this._formBuilder.group({
      nameCtrl: ['', Validators.required],
      addressCtrl : ['', Validators.required]

    });
    this.secondFormGroup = this._formBuilder.group({
      emailCtrl: ['', [Validators.required,Validators.email]],
      phoneCtrl : ['', Validators.required]
    });

    this.thirdFormGroup = this._formBuilder.group({
      passwordCtrl: ['', Validators.required],
      confirmPasswordCtrl : ['', Validators.required]
    });
    
  }


  registerEmployee() {
      //get the current route path
      let currentUrl = this.router.url
      let arrayOfUrls = currentUrl.split("/");
      if(arrayOfUrls[arrayOfUrls.length - 1] == 'adminSignup') {
          this.isAdminSignUp = true;
      }
      let user = new User(null,this.email.value,this.name.value,this.phoneNo.value,this.address.value,this.isAdminSignUp,null);     
       this.authService.registerUser(user,this.password.value).subscribe((employeeId) => {
        user.userId = employeeId;
        this.authService.loggedInUser = user;
        this.authService.loggedIn.next(true);
        if(user.isAdmin) {
          this.router.navigate(['./adminhomepage'])
        } else {
          this.router.navigate(['./userhomepage'])
        }   
      }, (loginError) => {
          this.errorMessage = loginError.error.message;  
      })
  }

 
  onPasswordChange() {
    if (this.confirm_password.value == this.password.value) {
      this.confirm_password.setErrors(null);
    } else {
      this.confirm_password.setErrors({ mismatch: true });
    }
  }
  
  // getting the form control elements
  get password(): AbstractControl {
    return this.thirdFormGroup.controls['passwordCtrl'];
  }

  get email(): AbstractControl {
    return this.secondFormGroup.controls['emailCtrl'];
  }

  get name(): AbstractControl {
    return this.firstFormGroup.controls['nameCtrl'];
  }
  get phoneNo(): AbstractControl {
    return this.secondFormGroup.controls['phoneCtrl'];
  }

  get address(): AbstractControl {
    return this.firstFormGroup.controls['addressCtrl'];
  }
  
  
  get confirm_password(): AbstractControl {
    return this.thirdFormGroup.controls['confirmPasswordCtrl'];
  }


  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }
}
