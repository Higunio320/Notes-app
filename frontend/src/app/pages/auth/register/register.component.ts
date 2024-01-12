import { Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {AuthService} from "../../../core/services/auth/auth.service";
import {Router, RouterLink} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {environment} from "../../../../environments/environment";
import {ApiUrl} from "../../../core/enums/api-url";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    RouterLink,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    NgIf,
    NgOptimizedImage
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  constructor(private authService: AuthService,
              private router: Router,
              private snackBar: MatSnackBar) {
  }

  hide = true;
  hideRepeat = true;
  waitingForResponse = false;

  registerForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    repeatPassword: new FormControl('', Validators.required),
  });

  submitForm() {
    if(this.registerForm.invalid) {
      this.snackBar.open('Please fill in all the fields', 'Close', {duration: 5000});
      return;
    }
    const username = this.registerForm.get('username')!.value;
    const password = this.registerForm.get('password')!.value;
    const firstName = this.registerForm.get('firstName')!.value;
    const lastName = this.registerForm.get('lastName')!.value;
    const repeatPassword = this.registerForm.get('repeatPassword')!.value;

    if(!username || !password || !firstName || !lastName || !repeatPassword) {
      return;
    }

    if(password !== repeatPassword) {
      this.snackBar.open('Passwords do not match', 'Close', {duration: 5000});
      return;
    }

    this.authService.register(username, password, firstName, lastName).subscribe({
      next: () => {this.router.navigate(['/home']);},
      error: error => {
        this.snackBar.open(error.message, 'Close')
      }}
    );
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
