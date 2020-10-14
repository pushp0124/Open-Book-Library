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
import { BookDetailComponent, NotifyAvailableComponent } from './book-detail/book-detail.component';
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
import { MatSlideToggleModule, MatCheckboxModule, MatDialogModule, MatRadioModule, MatDatepickerModule, MatNativeDateModule, MatSnackBarModule } from '@angular/material';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatExpansionModule} from '@angular/material/expansion';

//Charts
import { ChartsModule, ThemeService } from 'ng2-charts';
import { AdminCardActionComponent } from './admin-card-action/admin-card-action.component';
import { AddBookCategoryComponent } from './add-book-category/add-book-category.component';
import { DepositBookComponent } from './deposit-book/deposit-book.component';
import { BookListComponent } from './book-list/book-list.component';
import { AddBookAuthorComponent } from './add-book-author/add-book-author.component';
import { AddBookPublisherComponent } from './add-book-publisher/add-book-publisher.component';
import { DetailModifyComponent } from './detail-modify/detail-modify.component';
import { CategoryModifyComponent } from './category-modify/category-modify.component';
import { PublisherModifyComponent } from './publisher-modify/publisher-modify.component';
import { AuthorModifyComponent } from './author-modify/author-modify.component';
import { authInterceptorProviders } from './token-auth-interceptor.service';
import { UserBookReportComponent } from './user-book-report/user-book-report.component';

//Other select imports
import { NgxMatSelectSearchModule } from 'ngx-mat-select-search';
import { BookLostComponent } from './book-lost/book-lost.component';
import { DatePipe } from '@angular/common';
import { ReadMoreComponent } from './read-more/read-more.component';

//Angular Firestore
import { AngularFireModule } from "@angular/fire";
import { AngularFirestoreModule } from "@angular/fire/firestore"
import { environment } from 'src/environments/environment';
import { PatronFeedbackComponent } from './patron-feedback/patron-feedback.component';

//Carousel Module
import { CarouselModule } from 'ngx-owl-carousel-o';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { LibraryConstantsComponent } from './library-constants/library-constants.component';
import { LibraryInventoryReportComponent } from './library-inventory-report/library-inventory-report.component';
import { InventoryReportComponent } from './inventory-report/inventory-report.component';

@NgModule({
  entryComponents : [DetailModifyComponent, AuthorModifyComponent, PublisherModifyComponent, CategoryModifyComponent, BookLostComponent, NotifyAvailableComponent],
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
    DepositBookComponent,
    BookListComponent,
    AddBookAuthorComponent,
    AddBookPublisherComponent,
    DetailModifyComponent,
    CategoryModifyComponent,
    PublisherModifyComponent,
    AuthorModifyComponent,
    UserBookReportComponent,
    BookLostComponent,
    ReadMoreComponent,
    PatronFeedbackComponent,
    NotifyAvailableComponent,
    UserProfileComponent,
    LibraryConstantsComponent,
    LibraryInventoryReportComponent,
    InventoryReportComponent
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
    MatCheckboxModule,
    ChartsModule,
    MatAutocompleteModule,
    MatExpansionModule,
    MatDialogModule,
    MatRadioModule,
    MatDatepickerModule,
    MatNativeDateModule,
    NgxMatSelectSearchModule,
    MatSnackBarModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFirestoreModule,
    CarouselModule
  ],
  providers: [ThemeService, authInterceptorProviders, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
