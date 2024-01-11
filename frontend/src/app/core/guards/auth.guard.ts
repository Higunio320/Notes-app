import {CanActivateFn, Router, UrlTree} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../services/auth/auth.service";
import {map} from "rxjs";

export const authGuard: CanActivateFn = (route, state) => {
  let router = inject(Router);
  return inject(AuthService).isAuthenticated().pipe(
    map(isAuthenticated => {
      if(isAuthenticated) {
        return true;
      } else {
        return router.createUrlTree(['']);
      }
    })
  );
};
