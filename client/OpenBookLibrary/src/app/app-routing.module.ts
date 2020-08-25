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


const routes: Routes = [

  { path: 'appnav', component: AppNavComponent},
  { path: 'userhomepage',
    component: UserHomePageComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', component: UserHomeChildComponent }
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
  { path: '', component: HomeComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
