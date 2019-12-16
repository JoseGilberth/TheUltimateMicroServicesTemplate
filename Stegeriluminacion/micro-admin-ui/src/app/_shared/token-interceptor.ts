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

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    private intentos: number = 0;

    constructor(private util: UtilComponent
        , public router: Router) { }


    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));
        if (token != null && !request.url.includes("oauth/token")) {
            request = request.clone({
                headers: request.headers.set('Authorization', "Bearer " + token.access_token)
            });
        }
        return next.handle(request).pipe(
            //retry(2),
            retryWhen(errors => {
                return errors
                    .pipe(
                        delayWhen(() => timer(3000)),
                        tap((error) => {
                            if (request.method != "GET") {
                                this.intentos = 0;
                                throw new HttpErrorResponse(error);
                            }
                            if (this.intentos >= configuraciones.intentosError) {
                                this.intentos = 0;
                                throw new HttpErrorResponse({ error: 'bar', status: error.status });
                            }
                            this.util.presentToasterWarning("Intentando conectarse con el servidor");
                            this.intentos++;
                        })
                    );
            }),
            map((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    this.printRequest(request, event);
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
                    sessionStorage.clear( );
                    localStorage.clear();
                }
                return throwError(error);
            }));
    }


    printRequest(req: HttpRequest<any>, respuesta: any) {
        console.log(" ");
        console.log(" "); 
        console.log("Peticion URL: --------->>> " + req.url);
        console.log("Peticion METODO: ------>>> " + req.method);
        console.log("Peticion HEADERS: ----->>> " + JSON.stringify(req.headers));
        console.log('Peticion CONTENIDO: --->>> ' + JSON.stringify(req.body));
        console.log('Peticion RESPUESTA: --->>> ' + JSON.stringify(respuesta));
        console.log(" ");
        console.log(" "); 
    }

    validaReglasIntento(metodo: string, url: string, error: any) {
        if (
            (metodo == "GET" || metodo == "OPTIONS")
            || (url.includes("page") || url.includes("size"))
            && this.intentos >= configuraciones.intentosError
            && (error.status == 0 || (error.status >= 500 && error.status <= 599))) {
            this.intentos = 0;
             return true;
        }
         return false;
    }



}
