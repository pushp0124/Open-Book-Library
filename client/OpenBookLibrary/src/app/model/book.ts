import { BookCategory } from './bookCategory';
import { BookAuthor } from './bookAuthor';
import { BookPublisher } from './bookPublisher';

export class Book {
    bookId : number;
	bookTitle : string;
	bookCategory : BookCategory;
	bookAuthor : BookAuthor;
	bookPublisher : BookPublisher;
	bookDescription : string;
	bookRating : number;
	bookCopies : number;
	bookAvailableCopies : number;
	isAvailable : boolean;
	bookCost : number;
	bookEdition : string;
    bookImages : string[];
    

}