import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { LibraryAuthService } from '../auth-service';
import { Role } from '../model/role';

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
  isShowDetails: boolean = true;
  strength : number = 0;
  
  buttonTxt = "Register"
  isRegistering = false;
  ngOnInit() {
    
    this.firstFormGroup = this._formBuilder.group({
      firstNameCtrl: ['', [Validators.required,  Validators.pattern('^[0-9a-zA-Z ]*$')]],
      lastNameCtrl: ['', [Validators.required,  Validators.pattern('^[0-9a-zA-Z ]*$')]],
      addressCtrl : ['', [Validators.required, Validators.pattern("^[#.0-9a-zA-Z\s,-]+$")]]

    });
    this.secondFormGroup = this._formBuilder.group({
      emailCtrl: ['', [Validators.required,Validators.email]],
      phoneCtrl : ['', [Validators.required, Validators.pattern('(0/91)?[7-9][0-9]{9}')]]
    });

    this.thirdFormGroup = this._formBuilder.group({
      passwordCtrl: ['', [Validators.required]],
      confirmPasswordCtrl : ['', Validators.required]
    });
    
  }


  registerEmployee() {
      //get the current route path

      let roles = [];
     
      this.buttonTxt = "Registering ..."
      this.isRegistering = true
      let currentUrl = this.router.url
      let arrayOfUrls = currentUrl.split("/");
      if(arrayOfUrls[arrayOfUrls.length - 1] == 'adminsignup') {
          this.isAdminSignUp = true;
      }

      if(this.isAdminSignUp) {
        roles.push(new Role('ROLE_ADMIN'))
      } else {
        roles.push(new Role('ROLE_USER'))
      }
      
      let user = new User(null,this.email.value,this.fName.value + " " + this.lName.value,this.phoneNo.value,this.address.value,this.password.value, roles);
      console.log(user); 
       this.authService.registerUser(user).subscribe((employee) => {
          this.successMessage = "We have all your details safe, please access your mail for its verification";
          this.isRegistering = false
          this.buttonTxt = "Register"
          this.thirdFormGroup.disable();


      }, (registerError) => {
          this.errorMessage = registerError;  
          this.buttonTxt = "Register"
          this.isRegistering = false
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

  get fName(): AbstractControl {
    return this.firstFormGroup.controls['firstNameCtrl'];
  }

  get lName(): AbstractControl {
    return this.firstFormGroup.controls['lastNameCtrl'];
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

  onStrengthChanged(strength: number) {
    this.strength = strength;
  }

  changed() {
    this.isShowDetails = !this.isShowDetails;
  }

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }
}