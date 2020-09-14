import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppNavComponent } from './app-nav/app-nav.component';
import { AdminHomePageComponent } from './admin-home-page/admin-home-page.component';
import { UserHomePageComponent } from './user-home-page/user-home-page.component';
import { AdminHomeChildComponent } from './admin-home-child/admin-home-child.component';
import { UserHomeChildComponent } from './user-home-child/user-home-child.component';
import { HomeComponent } from './home/home.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatButtonModule} from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import {MatStepperModule} from '@angular/material/stepper';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTooltipModule} from '@angular/material/tooltip';
import { UserLoginComponent } from './user-login/user-login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminAddBookComponent } from './admin-add-book/admin-add-book.component';
import { MyBooksComponent } from './my-books/my-books.component';
import {MatTableModule} from '@angular/material/table';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { MatSortModule } from '@angular/material/sort';
import {CdkTableModule} from '@angular/cdk/table';
import { MatPasswordStrengthModule } from '@angular-material-extensions/password-strength';
import { MatSlideToggleModule } from '@angular/material';
import {MatAutocompleteModule} from '@angular/material/autocomplete';

//Charts
import { ChartsModule, ThemeService } from 'ng2-charts';
import { AdminCardActionComponent } from './admin-card-action/admin-card-action.component';
import { AddBookCategoryComponent } from './add-book-category/add-book-category.component';
import { DepositBookComponent } from './deposit-book/deposit-book.component';


@NgModule({
  declarations: [
    AppComponent,
    AppNavComponent,
    AdminHomePageComponent,
    UserHomePageComponent,
    AdminHomeChildComponent,
    UserHomeChildComponent,
    HomeComponent,
    PageNotFoundComponent,
    BookDetailComponent,
    UserLoginComponent,
    SignUpComponent,
    AdminAddBookComponent,
    MyBooksComponent,
    AdminCardActionComponent,
    AddBookCategoryComponent,
    DepositBookComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    MatIconModule,
    MatCardModule,
    MatGridListModule,
    MatButtonModule,
    MatStepperModule,
    MatInputModule,
    MatSelectModule,
    MatTooltipModule,
    MatPaginatorModule,
    MatTableModule,
    MatSortModule,
    ReactiveFormsModule,
    MatProgressBarModule,
    CdkTableModule,
    MatPasswordStrengthModule,
    MatSlideToggleModule,
    ChartsModule,
    MatAutocompleteModule
  ],
  providers: [ThemeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
