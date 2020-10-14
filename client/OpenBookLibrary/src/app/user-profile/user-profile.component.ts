import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { LibraryAuthService } from '../auth-service';
import { User } from '../model/user';
import { HttpParams } from '@angular/common/http';
import { UpdateUserProfile } from '../model/updateUserProfile';
import { AuthRequest } from '../model/authRequest';
import { ChangePasswordRequest } from '../model/changePasswordRequest';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  isPersonalInfoEditable = false;

  personalInfoButtonText = "Edit";

  profileFormGroup: FormGroup;

  changePasswordFormGroup : FormGroup;

  errorMessage: string;
  successMessage: string;
  isShowDetails: boolean = true;
  strength: number = 0;

  isSecondExpansionPanelHidden = false
  hide = true;

  userProfile: User;

  constructor(private _formBuilder: FormBuilder, private authService: LibraryAuthService) { }

  ngOnInit() {

    this.changePasswordFormGroup = this._formBuilder.group({
      oldPasswordCtrl : ['', [Validators.required]],
      newPasswordCtrl: ['', [Validators.required]],
      confirmPasswordCtrl : ['', [Validators.required]]
    });

    this.authService.getUserProfile(this.authService.getAuthResponse().loggedInUserId).subscribe((user) => {
      this.userProfile = user;
      this.profileFormGroup = this._formBuilder.group({
        nameCtrl: [user.userName, [Validators.required, Validators.pattern('^[0-9a-zA-Z ]*$')]],
        addressCtrl: [user.userAddress, [Validators.required, Validators.pattern("^[#.0-9a-zA-Z\s,-]+$")]],
        emailCtrl: [user.userEmail, [Validators.required, Validators.email]],
        phoneCtrl: [user.userPhoneNo, [Validators.required, Validators.pattern('(0/91)?[7-9][0-9]{9}')]],
        passwordCtrl: ['', [Validators.required]],
       
      });
      this.profileFormGroup.disable() 
    }, (profileError) => {
      this.errorMessage = profileError
    })


  }

  toggleEdit() {
    this.isPersonalInfoEditable = !this.isPersonalInfoEditable
    if (this.isPersonalInfoEditable) {
      this.personalInfoButtonText = "Cancel"
      this.profileFormGroup.enable()
    } else {
      this.personalInfoButtonText = "Edit"
      this.profileFormGroup.disable()
    }
  }



  updateUserProfile() {
    let userCurrentEmail = this.userProfile.userEmail
    this.userProfile.userAddress = this.address.value
    this.userProfile.userEmail = this.email.value
    this.userProfile.userPhoneNo = this.phoneNo.value
    this.userProfile.userName = this.name.value
    
    let updatedUserProfile = new UpdateUserProfile()
    updatedUserProfile.updatedUser = this.userProfile

    let authRequest  = new AuthRequest();
    authRequest.username = userCurrentEmail
    authRequest.password = this.password.value
    updatedUserProfile.authRequest = authRequest

    this.authService.updateUserProfile(updatedUserProfile).subscribe((isSuccess) => {
          this.authService.logout();
    }, (error) => {
      this.errorMessage = error
    })

  }

  changePassword() {

    let changePasswordRequest = new ChangePasswordRequest();
    changePasswordRequest.newPassword = this.new_password.value
    changePasswordRequest.oldPassword = this.old_password.value
    changePasswordRequest.userName = this.userProfile.userEmail
    this.authService.changePassword(changePasswordRequest).subscribe((isSuccess) => {
      this.authService.logout();
    }, (error) => {
      this.errorMessage = error
    })

  }



  onPasswordChange() {
    if (this.confirm_password.value == this.new_password.value) {
      this.confirm_password.setErrors(null);
    } else {
      this.confirm_password.setErrors({ mismatch: true });
    }
  }

  // getting the form control elements
  get password(): AbstractControl {
    return this.profileFormGroup.controls['passwordCtrl'];
  }

  get email(): AbstractControl {
    return this.profileFormGroup.controls['emailCtrl'];
  }

  get name(): AbstractControl {
    return this.profileFormGroup.controls['nameCtrl'];
  }


  get phoneNo(): AbstractControl {
    return this.profileFormGroup.controls['phoneCtrl'];
  }

  get address(): AbstractControl {
    return this.profileFormGroup.controls['addressCtrl'];
  }



 //change password form group
  get new_password() : AbstractControl {
    return this.changePasswordFormGroup.controls['newPasswordCtrl'];
  }

  get old_password() : AbstractControl {
    return this.changePasswordFormGroup.controls['oldPasswordCtrl'];
  }

  get confirm_password(): AbstractControl {
    return this.changePasswordFormGroup.controls['confirmPasswordCtrl'];
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
