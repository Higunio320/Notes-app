import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {StorageService} from "../storage/storage.service";
import {LoginResponse} from "../../data/auth/login-response";
import {environment} from "../../../../environments/environment";
import {ApiUrl} from "../../enums/api-url";
import {tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authenticated = false;

  constructor(private http: HttpClient,
              private storage: StorageService) {}

  login(username: string, password: string) {
    return this.http.post<LoginResponse>(`${environment.API_URL}${ApiUrl.LOGIN}` , {email: username, password: password}).pipe(
      tap({
        next: (response) => {
          this.saveToken(response.token);
        },
      })
    );
  }

  logout() {
    this.storage.deleteToken();
  }

  private saveToken(token: string) {
    this.storage.saveToken(token);
    this.authenticated = true;
  }

  isAuthenticated() {
    const token = this.storage.getToken();

    this.authenticated = token != null && token !== '';

    return this.authenticated;
  }
}
