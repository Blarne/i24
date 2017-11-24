import {NgModule} from '@angular/core';
import {AuthService} from '../../security/auth/auth.service';
import {AuthInterceptor} from '../../shared/jewelry.interceptor';
import {AuthRouter} from './auth.routes';
import {LoginComponent} from './login/login.component';
import {HttpClient, HTTP_INTERCEPTORS} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {SharedModule} from 'app/shared/shared.module';

@NgModule({
  declarations: [LoginComponent],
  imports: [AuthRouter, SharedModule],
  providers: [
    HttpClient,
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  exports: [RouterModule]
})
export class AuthModule {}
