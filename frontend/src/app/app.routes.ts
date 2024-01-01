import { Routes } from '@angular/router';
import {LoginComponent} from "./pages/auth/login/login.component";
import {NotesComponent} from "./pages/home/notes/notes.component";

export const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },
  { path: 'auth', children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      ]},
  { path: 'home', children: [
      { path: '', redirectTo: 'notes', pathMatch: 'full' },
      { path: 'notes', component: NotesComponent},
      { path: '**', redirectTo: 'notes'}
    ]},
  { path: '**', redirectTo: 'auth' }
];
