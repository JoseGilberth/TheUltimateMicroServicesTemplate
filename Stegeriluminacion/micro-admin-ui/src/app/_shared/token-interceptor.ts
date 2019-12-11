import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import 'rxjs/add/observable/fromPromise';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/switchMap';
 import { Respuesta } from '../_dto/_main/Respuesta.Dto';
import { NotificationComponent } from './notification.component';
import { Token } from '../_dto/login/Token.Dto';
import { configuraciones } from '../../environments/configuraciones';
import { Observable } from 'rxjs';
 
@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(private router: Router, private notificacion: NotificationComponent) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));
        if (token != null && !request.url.includes("oauth/token")) {
            request = request.clone({
                headers: request.headers.set('Authorization', "Bearer " + token.access_token)
            });
        }
        return next.handle(request)
            .map((event: any) => {
                if (event instanceof HttpResponse /*&& ~~(event.status / 100) > 3*/) {
                    try {
                        let respuesta: Respuesta = <Respuesta>event.body;
                        if (respuesta.exitoso == false) {
                            let error: HttpErrorResponse = new HttpErrorResponse({
                                error: null
                                , headers: null
                                , status: respuesta.codigo
                                , statusText: respuesta.mensaje, url: ""
                            });
                            this.errores(error);
                            return Observable.throw(error);
                        }
                    } catch (ex) { }
                }
                return event;
            })
            .catch((err: any, caught) => {
                if (err instanceof HttpErrorResponse) {
                    this.errores(err);
                }
                return Observable.throw(err);
            });
    }


    errores(error: HttpErrorResponse) {
        if (error.status === 401 || error.status === 403) {
            this.notificacion.showNotification("No autorizado", 'top', 'right', 'danger');
            //this.router.navigate(['/login']);
        } else if (error.status === 400) {
            this.notificacion.showNotification(error.error.error_description + " : " + error.error.error, 'top', 'right', 'danger');
        } else if (error.status >= 500 && error.status < 600) {
            this.notificacion.showNotification(error.error.mensaje, 'top', 'right', 'danger');
        } else if (error.status === 0) {
            this.notificacion.showNotification("Error desconocido", 'top', 'right', 'danger');
        } else {

        }
    }

}
