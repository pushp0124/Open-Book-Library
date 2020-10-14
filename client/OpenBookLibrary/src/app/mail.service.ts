import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BookTransaction } from './model/bookTransaction';
import { Observable } from 'rxjs';
import { MailResponse } from './model/mailResponse';

@Injectable({
  providedIn: 'root'
})
export class MailService {

  private baseUrl = "http://localhost:8102"
  constructor(private http: HttpClient) { }

  notifyDelayedMembers(issueBooks : BookTransaction[]) {
      return this.http.post(this.baseUrl + "/notify/book-delay", issueBooks);
  }
}
