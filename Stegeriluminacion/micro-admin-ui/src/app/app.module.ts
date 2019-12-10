import { CommonModule } from '@angular/common';
//HTTP
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DateAdapter, MatTooltipModule, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { JwtModule } from '@auth0/angular-jwt';
// TRANSLATE
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
// CONFIGURACIONES 
import { configuraciones } from 'src/environments/configuraciones';
import { AppComponent } from './app.component';
// RUTAS
import { AppRoutes } from './app.routing';
//LAYOUT
import { AdminLayoutComponent } from './pages/_layout_master/admin/admin-layout.component';
import { AuthLayoutComponent } from './pages/_layout_master/auth/auth-layout.component';
import { FixedpluginModule } from './pages/_layout_master/fixedplugin/fixedplugin.module';
import { FooterModule } from './pages/_layout_master/footer/footer.module';
import { NavbarModule } from './pages/_layout_master/navbar/navbar.module';
//MASTER
import { SidebarModule } from './pages/_layout_master/sidebar/sidebar.module';
//SEGURIDAD
import { AuthGuardService } from './_guards/auth-guard.service';
import { PermissionGuardService } from './_guards/permission-guard.service';
//UTIL
import { NotificationComponent } from './_shared/notification.component';
import { TokenInterceptor } from './_shared/token-interceptor';
import { UtilComponent } from './_shared/util.component';
import { Token } from './_dto/login/Token.Dto';

// AoT requires an exported function for factories
export function createTranslateLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

export function tokenGetter() {
    let token: Token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));
    if (token != null) {
        return token.access_token;
    }
    return null;
}

export const MY_FORMATS = {
    parse: {
        dateInput: 'DD/MM/YYYY',
    },
    display: {
        dateInput: 'DD/MM/YYYY',
        monthYearLabel: 'MM YYYY',
        dateA11yLabel: 'DD/MM/YYYY',
        monthYearA11yLabel: 'MM YYYY',
    },
};

@NgModule({
    exports: []
})
export class MaterialModule { }


@NgModule({
    imports: [
        CommonModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule.forRoot(AppRoutes, { useHash: true }),
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: (createTranslateLoader),
                deps: [HttpClient]
            }
        }),
        JwtModule.forRoot({
            config: {
                tokenGetter: tokenGetter,
                whitelistedDomains: [''],
                blacklistedRoutes: ['']
            }
        }),
        HttpClientModule,
        MaterialModule,
        SidebarModule,
        NavbarModule,
        FooterModule,
        FixedpluginModule,
        MatTooltipModule
    ],
    declarations: [
        AppComponent,
        AdminLayoutComponent,
        AuthLayoutComponent
    ],
    providers: [
        [AuthGuardService, PermissionGuardService],
        NotificationComponent,
        UtilComponent,
        { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
        { provide: MAT_DATE_LOCALE, useValue: 'es' }, //you can change useValue
        { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
        { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS }
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
