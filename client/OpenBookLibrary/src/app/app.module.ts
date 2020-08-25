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
    BookDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
