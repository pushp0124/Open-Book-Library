import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AngularFirestore } from '@angular/fire/firestore';
import { FireUser } from './model/firebase_model/fire_user';
import { FireFeedback } from './model/firebase_model/fire_feedback';
import { FireChatMessages } from './model/firebase_model/fire_chatMessages';

@Injectable({
  providedIn: 'root'
})
export class FirestoreChatService {


  constructor(private firestore: AngularFirestore) { }

  getFeedbacks(): Observable<FireFeedback[]> {
    return this.firestore.collection<FireFeedback>('feedback_chats', ref => 
      ref.orderBy('timestamp')
    ).valueChanges();
  }

  addFeedback(feedback : FireFeedback) {
    let fireFeedCollection = this.firestore.collection<FireFeedback>('feedback_chats')
    fireFeedCollection.add({... feedback})
  }

  getUsers() {
    return this.firestore.collection<FireUser>('users').valueChanges()
  }

  sendMessage(chatId, chatMessage : FireChatMessages) {
    let fireChatMessageCollection = this.firestore.collection<FireChatMessages>('chat_messages/' + chatId)
    fireChatMessageCollection.add({... chatMessage})
  }

  getChatMessages(chatId) : Observable<FireChatMessages[]>{
    return this.firestore.collection<FireChatMessages>('chat_messages').valueChanges()
  }


}
