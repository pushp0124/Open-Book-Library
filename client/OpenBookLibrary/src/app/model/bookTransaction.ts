import { User } from './user';
import { Book } from './book';
import { BookTransactionStatus } from './bookTransactionStatus';

export class BookTransaction {
    transactionId : number ;
    borrowedToUser : User;
	borrowedDate : Date;
	returnDate : Date;
	bookStatus : BookTransactionStatus; 
	borrowedBook : Book;
	fineTillDate : number;
}