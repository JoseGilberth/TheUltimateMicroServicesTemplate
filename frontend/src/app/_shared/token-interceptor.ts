import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError, timer } from 'rxjs';
import 'rxjs/add/observable/fromPromise';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/switchMap';
import { catchError, delayWhen, map, retryWhen, tap } from 'rxjs/operators';
import { configuraciones } from '../../environments/configuraciones';
import { UtilComponent } from './util.component';
import { Token } from '../_dto/login/Token.Dto';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(private util: UtilComponent, public router: Router) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));
        if (token != null && !request.url.includes("oauth/token")) {
            request = request.clone({
                headers: request.headers.set('Authorization', "Bearer " + token.access_token ).set('Accept-Language', "es") 
            });
        }
        return next.handle(request).pipe(
            map((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    if (!environment.production) {
                        this.printRequest(request, event);
                    }
                }
                return event;
            }),
            catchError((error: HttpErrorResponse) => {
                this.util.trataErrores(error);
                if (error.status === 0) {
                    this.util.presentToasterWarning("Error de conexión");
                }
                if (error.status === 401) {
                    this.router.navigate(['/login']);
                    sessionStorage.clear();
                    localStorage.clear();
                }
                return throwError(error);
            }));
    }


    printRequest(req: HttpRequest<any>, respuesta: any) {
        console.log(" ");
        console.log(" ");
        console.log(" ");
        console.log("Peticion URL: --------->>> " + req.url);
        console.log("Peticion METODO: ------>>> " + req.method);
        console.log("Peticion HEADERS: ----->>> " + JSON.stringify(req.headers));
        console.log('Peticion CONTENIDO: --->>> ' + JSON.stringify(req.body, null, 4));
        console.log('Peticion RESPUESTA: --->>> ' + JSON.stringify(respuesta, null, 4));
        console.log(" ");
        console.log(" ");
        console.log(" ");
    }

}
