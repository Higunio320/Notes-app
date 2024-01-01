import { Routes } from '@angular/router';
import {LoginComponent} from "./pages/auth/login/login.component";

export const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },
  { path: 'auth', children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      ]},
  { path: '**', redirectTo: 'auth/login' }
];
