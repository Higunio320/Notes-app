import { Routes } from '@angular/router';
import {LoginComponent} from "./pages/auth/login/login.component";
import {NotesComponent} from "./pages/home/notes/notes.component";
import {OauthComponent} from "./pages/oauth/oauth.component";
import {authGuard} from "./core/guards/auth.guard";
import {RegisterComponent} from "./pages/auth/register/register.component";

export const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },
  { path: 'auth', children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent},
      { path: '**', redirectTo: 'login'}
      ]},
  { path: 'home', canActivate: [authGuard], children: [
      { path: '', redirectTo: 'notes', pathMatch: 'full' },
      { path: 'notes', component: NotesComponent},
      { path: '**', redirectTo: 'notes'}
    ]},
  { path: 'oauth2', component: OauthComponent},
  { path: '**', redirectTo: 'auth/login'}
];
