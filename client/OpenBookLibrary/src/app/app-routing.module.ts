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


const routes: Routes = [

  { path: 'appnav', component: AppNavComponent},
  { path: 'userlogin', component: UserLoginComponent},
  { path: 'userhomepage',
    component: UserHomePageComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', component: UserHomeChildComponent },
      { path : 'book-detail', component : BookDetailComponent}
    ]
  },
  {
    path: 'adminhomepage',
    component: AdminHomePageComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', component: AdminHomeChildComponent },
    ]

  },
  { path : 'encrypted/adminsignup' ,component : SignUpComponent},
  { path: 'signup', component: SignUpComponent },
  { path: '', component: HomeComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
