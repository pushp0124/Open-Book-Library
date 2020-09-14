import { User } from './user';
import { Book } from './book';
import { IssueBookStatus } from './issueBookStatus';

export class IssueBook {
    issueId : number ;
    issuedToUser : User;
	issueDate : Date;
	returnDate : Date;
	bookStatus : IssueBookStatus; 
	issuedBook : Book;
}