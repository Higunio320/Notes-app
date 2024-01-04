import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../services/auth/auth.service";

export const authGuard: CanActivateFn = (route, state) => {
  if(inject(AuthService).isAuthenticated()) {
    return true;
  } else {
    inject(Router).navigate(['auth/login']);
    return false;
  }
};
