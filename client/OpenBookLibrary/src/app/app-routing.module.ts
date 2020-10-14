import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppNavComponent } from './app-nav/app-nav.component';
import { UserHomePageComponent } from './user-home-page/user-home-page.component';
import { AuthGuard } from './auth.guard';
import { UserHomeChildComponent } from './user-home-child/user-home-child.component';
import { AdminHomePageComponent } from './admin-home-page/admin-home-page.component';
import { AdminHomeChildComponent } from './admin-home-child/admin-home-child.component';
import { HomeComponent } from './home/home.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { AdminAddBookComponent } from './admin-add-book/admin-add-book.component';
import { MyBooksComponent } from './my-books/my-books.component';
import { AddBookCategoryComponent } from './add-book-category/add-book-category.component';
import { DepositBookComponent } from './deposit-book/deposit-book.component';
import { BookListComponent } from './book-list/book-list.component';
import { AddBookPublisherComponent } from './add-book-publisher/add-book-publisher.component';
import { AddBookAuthorComponent } from './add-book-author/add-book-author.component';
import { UserBookReportComponent } from './user-book-report/user-book-report.component';
import { PatronFeedbackComponent } from './patron-feedback/patron-feedback.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { LibraryConstantsComponent } from './library-constants/library-constants.component';
import { InventoryReportComponent } from './inventory-report/inventory-report.component';


export const routes: Routes = [

  { path: 'appnav', component: AppNavComponent },
  {
    path: 'userlogin', component: UserLoginComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '',
    component: UserHomePageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_USER'] },
    children: [
      {
        path: 'catalog/:categoryId',
        children: [
          { path: '', component: UserHomeChildComponent },
          { path: 'book/:id', component: BookDetailComponent },
          {path : 'profile', component : UserProfileComponent}
        ]
      }
      ,
      {
        path: 'my-books',
        children: [
          { path: '', component: MyBooksComponent },
          { path: 'book/:id', component: BookDetailComponent }
        ]
      },
      { path : 'library-feedback', component : PatronFeedbackComponent}
    ]
  }, 
{
  path: 'adminhomepage',
    component: AdminHomePageComponent,
      canActivate: [AuthGuard],
        data: { roles: ['ROLE_ADMIN'] },
  children: [
    { path: '', component: AdminHomeChildComponent },
    { path: 'add-book', component: AdminAddBookComponent },
    { path: 'book-list', component: BookListComponent },
    { path: 'add-book-category', component: AddBookCategoryComponent },
    { path: 'add-book-author', component: AddBookAuthorComponent },
    { path: 'add-book-publisher', component: AddBookPublisherComponent },
    { path: 'user-book-report', component: UserBookReportComponent },
    { path: 'book-transactions', component: DepositBookComponent },
    {path : 'profile', component : UserProfileComponent},
    {path : 'inventory-report', component : InventoryReportComponent},
    {path : 'library-feedback', component : PatronFeedbackComponent},
    {path : 'settings', component : LibraryConstantsComponent}
  ]

},
{
  path: 'encrypted/adminsignup', component: SignUpComponent,
    canActivate: [AuthGuard]
},
{
  path: 'signup', component: SignUpComponent,
    canActivate: [AuthGuard]
},
{
  path: '', component: HomeComponent,
    canActivate: [AuthGuard]
},
{ path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
