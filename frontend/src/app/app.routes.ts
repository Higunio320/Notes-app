import { Routes } from '@angular/router';
import {LoginComponent} from "./pages/auth/login/login.component";
import {HomeComponent} from "./pages/home/home.component";
import {OauthComponent} from "./pages/oauth/oauth.component";
import {authGuard} from "./core/guards/auth.guard";
import {RegisterComponent} from "./pages/auth/register/register.component";
import {NotesListComponent} from "./pages/home/notes-list/notes-list.component";
import {NoteAddComponent} from "./pages/home/note-add/note-add.component";

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
      { path: 'notes', component: HomeComponent, children: [
          { path: '', redirectTo: 'list', pathMatch: 'full' },
          { path: 'list', component: NotesListComponent},
          { path: 'add', component: NoteAddComponent},
          { path: '**', redirectTo: 'list'}
        ]},
      { path: '**', redirectTo: 'notes'}
    ]},
  { path: 'oauth2', component: OauthComponent},
  { path: '**', redirectTo: 'auth/login'}
];
