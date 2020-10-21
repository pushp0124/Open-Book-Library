import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { LibraryAuthService } from '../auth-service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  
  errorMessage : string;
  successMessage : string;
  
  forgotPasswordFormGroup : FormGroup;

  constructor(private _formBuilder: FormBuilder, private authService : LibraryAuthService) { }

  ngOnInit() {
    this.forgotPasswordFormGroup = this._formBuilder.group({
      emailCtrl: ['', [Validators.email, Validators.required]],
      
    });
  }

  get emailCtrl(): AbstractControl {
    return this.forgotPasswordFormGroup.controls['emailCtrl'];
  }

  forgotPassword() {
    this.authService.forgotPassword(this.emailCtrl.value).subscribe((isSuccess) => {
      this.successMessage = "We have sent you an email, with new password!"
    }, (error) => {
      this.errorMessage = error
    })
    
  }

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }
}
