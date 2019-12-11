import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { JwtModule } from '@auth0/angular-jwt';
import { AppAsideModule, AppBreadcrumbModule, AppFooterModule, AppHeaderModule, AppSidebarModule } from '@coreui/angular';
import { ChartsModule } from 'ng2-charts';
// Import 3rd party components
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { configuraciones } from '../environments/configuraciones';
import { AppComponent } from './app.component';
// Import routing module
import { AppRoutingModule } from './app.routing';
import { P404Component } from './pages/administracion/error/404.component';
import { P500Component } from './pages/administracion/error/500.component';
import { RegisterComponent } from './pages/administracion/register/register.component';
import { DefaultLayoutComponent } from './pages/_layout_master';
import { LoginComponent } from './pages/_login/login.component';
import { Token } from './_dto/login/Token.Dto';
import { AuthGuardService } from './_guards/auth-guard.service';
import { PermissionGuardService } from './_guards/permission-guard.service';
import { NotificationComponent } from './_shared/notification.component';
import { TokenInterceptor } from './_shared/token-interceptor';
import { UtilComponent } from './_shared/util.component';


const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

// Import containers 
const APP_CONTAINERS = [
  DefaultLayoutComponent
];

export function tokenGetter() {
  let token: Token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));
  if (token != null) {
    return token.access_token;
  }
  return null;
}

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AppAsideModule,
    AppBreadcrumbModule.forRoot(),
    AppFooterModule,
    AppHeaderModule,
    AppSidebarModule,
    PerfectScrollbarModule,
    BsDropdownModule.forRoot(),
    TabsModule.forRoot(),
    ChartsModule,
    FormsModule,
    ReactiveFormsModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        whitelistedDomains: [''],
        blacklistedRoutes: ['']
      }
    }),
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    ...APP_CONTAINERS,
    P404Component,
    P500Component,
    LoginComponent,
    RegisterComponent
  ],
  providers: [
    [AuthGuardService, PermissionGuardService],
    NotificationComponent,
    UtilComponent,
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
