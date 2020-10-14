export class InventoryReport {
    totalBooks : number; // available + unavailable (lost + borrowed)
    totalMembers : number; // all including admins
    totalPriceOfBooks : number; // sum of all books price
    totalBooksLost : number; // lost books count
    borrowedBooks : number;
}