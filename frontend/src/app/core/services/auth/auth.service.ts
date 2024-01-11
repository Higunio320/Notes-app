import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {StorageService} from "../storage/storage.service";
import {LoginResponse} from "../../data/auth/login-response";
import {environment} from "../../../../environments/environment";
import {ApiUrl} from "../../enums/api-url";
import {catchError, map, Observable, of, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

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
  }

  isAuthenticated(): Observable<boolean> {
    let token = this.storage.getToken();
    if(token) {
      let params = new HttpParams().set('token', token);
      return this.http.get(`${environment.API_URL}${ApiUrl.CHECK_TOKEN}`, {params, observe: 'response'}).pipe(
        map(response => {
          if(response.status === 200) {
            return true;
          } else {
            this.storage.deleteToken();
            return false;
          }
        }),
        catchError(() => {
          this.storage.deleteToken();
          return of(false);
        })
      );
    } else {
      return of(false);
    }
  }
}
