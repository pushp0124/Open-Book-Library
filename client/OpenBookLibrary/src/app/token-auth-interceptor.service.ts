import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LibraryAuthService } from './auth-service';
import { ErrorInterceptor } from './error-interceptor.service';


@Injectable({
  providedIn: 'root'
})
export class TokenAuthInterceptor implements HttpInterceptor {
  constructor(private authenticationService: LibraryAuthService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    //   add authorization header with basic auth credentials if available
      let authResponse = this.authenticationService.getAuthResponse();
      
      if (authResponse != null && authResponse.accessToken != null) {
          request = request.clone({
              setHeaders: { 
                  Authorization: `Bearer ${authResponse.accessToken}`
              }
          });
      }
        console.log(request)
      return next.handle(request);
  }
}
export const authInterceptorProviders = [
    { provide: HTTP_INTERCEPTORS, useClass: TokenAuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ];