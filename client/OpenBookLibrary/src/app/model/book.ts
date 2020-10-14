import { BookCategory } from './bookCategory';
import { BookAuthor } from './bookAuthor';
import { BookPublisher } from './bookPublisher';
import { User } from './user';

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
	toNotifyUsers : User[];

	constructor(bookTitle : string, bookCategory : BookCategory, bookAuthor : BookAuthor, bookPublisher : BookPublisher, bookDescription : string,bookCopies :number, bookCost : number, bookEdition : string, bookImages : string[], bookIsAvailable : boolean) {
		this.bookTitle = bookTitle
		this.bookCategory = bookCategory
		this.bookAuthor = bookAuthor
		this.bookPublisher = bookPublisher
		this.bookDescription = bookDescription
		this.bookCopies = bookCopies
		this.bookCost = bookCost
		this.bookEdition = bookEdition
		this.bookImages = bookImages
		this.bookRating = 5.0
		this.isAvailable = bookIsAvailable
		this.toNotifyUsers = []
	}

}