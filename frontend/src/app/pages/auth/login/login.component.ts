import { Component } from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {AuthService} from "../../../core/services/auth/auth.service";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {environment} from "../../../../environments/environment";
import {ApiUrl} from "../../../core/enums/api-url";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    FormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatIconModule,
    NgOptimizedImage,
    MatProgressSpinnerModule,
    NgIf,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  constructor(private authService: AuthService,
              private router: Router) { }


  hidePassword = true;
  waitingForResponse = false;

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });


  submitForm() {
    const username = this.loginForm.get('username')!.value;
    const password = this.loginForm.get('password')!.value;

    if(!username || !password) {
      return;
    }

    this.authService.login(username, password).subscribe({
      next: () => {this.router.navigateByUrl('/home')},
    });
  }

  authorizeWithGoogle() {
    this.waitingForResponse = true;
    window.location.href = `${environment.API_URL}${ApiUrl.OAUTH_GOOGLE}`;
  }

  authorizeWithGitHub() {
    this.waitingForResponse = true;
    window.location.href = `${environment.API_URL}${ApiUrl.OAUTH_GITHUB}`;
  }

}
