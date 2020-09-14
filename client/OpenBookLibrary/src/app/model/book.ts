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
	
	constructor(bookTitle : string, bookCategory : BookCategory, bookAuthor : BookAuthor, bookPublisher : BookPublisher, bookDescription : string,bookCopies :number, bookCost : number, bookEdition : string, bookImages : [string]) {
		this.bookTitle = bookTitle
		this.bookCategory = bookCategory
		this.bookAuthor = bookAuthor
		this.bookPublisher = bookPublisher
		this.bookDescription = bookDescription
		this.bookCopies = bookCopies
		this.bookCost = bookCost
		this.bookEdition = bookEdition
		this.bookImages = bookImages
	}

}