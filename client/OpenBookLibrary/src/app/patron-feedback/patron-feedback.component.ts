import { Component, OnInit } from '@angular/core';
import { FirestoreChatService } from '../firestore-chat.service';
import { LibraryAuthService } from '../auth-service';
import { FireFeedback } from '../model/firebase_model/fire_feedback';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-patron-feedback',
  templateUrl: './patron-feedback.component.html',
  styleUrls: ['./patron-feedback.component.css']
})
export class PatronFeedbackComponent implements OnInit {

  feedbacks : FireFeedback[]
  loggedInUserId : number;
  loggedInUserEmail : string;

  dataLoaded = false;

  feedbackFormGroup : FormGroup;

  errorMessage = undefined
  successMessage = undefined


  constructor(private chatService : FirestoreChatService, private authService : LibraryAuthService, private _formBuilder : FormBuilder) { }

  ngOnInit() {
    this.feedbackFormGroup = this._formBuilder.group({
      feedbackCtrl: ['', [Validators.required]]

    });
    this.loggedInUserId = this.authService.getAuthResponse().loggedInUserId
    this.loggedInUserEmail = this.authService.getAuthResponse().loggedInUserEmail
    this.chatService.getFeedbacks().subscribe((feedbacks) => {
        this.feedbacks = feedbacks
        this.dataLoaded = true
    }, (error) => {
      this.errorMessage = error
      this.dataLoaded = false
    })
  }

  addFeedback() {
    
    let feedback = new FireFeedback()
    feedback.message = this.feedbackCtrl.value
    feedback.senderId = this.loggedInUserId
    feedback.senderEmail = this.loggedInUserEmail
    feedback.timestamp = + new Date()
    console.log(feedback);
    
    this.chatService.addFeedback(feedback)
  }

  get feedbackCtrl(): AbstractControl {
    return this.feedbackFormGroup.controls['feedbackCtrl'];
  }

  errorClosed() {
    this.errorMessage = undefined;
  }

  successClosed() {
    this.successMessage = undefined;
  }

}
