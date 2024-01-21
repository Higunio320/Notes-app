import { ApplicationConfig, isDevMode } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideAnimations} from "@angular/platform-browser/animations";
import {authInterceptor} from "./core/interceptors/auth.interceptor";
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import { provideServiceWorker } from '@angular/service-worker';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimations(),
    provideHttpClient(withInterceptors([authInterceptor])),
    provideAnimations(),
    provideServiceWorker('ngsw-worker.js', {
        enabled: true,
        registrationStrategy: 'registerWhenStable:30000'
    })
]
};
