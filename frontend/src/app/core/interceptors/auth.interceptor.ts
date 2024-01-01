import { HttpInterceptorFn } from '@angular/common/http';
import {StorageService} from "../services/storage/storage.service";
import {inject} from "@angular/core";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const storageService = inject(StorageService);
  const token = storageService.getToken();

  if(token) {
    const authReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });

    return(next(authReq));
  } else {
    return next(req);
  }
};
