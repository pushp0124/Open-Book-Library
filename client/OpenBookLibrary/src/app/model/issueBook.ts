import { User } from './user';
import { Book } from './book';

export class IssueBook {
    issueId : number ;
    issuedToUser : User;
	issueDate : Date;
	returnDate : Date;
	issuedBook : Book;
}