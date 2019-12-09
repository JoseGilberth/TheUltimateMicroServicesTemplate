import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';

import { JwtHelperService } from '@auth0/angular-jwt';
import { configuraciones } from 'src/environments/configuraciones';
import { UtilComponent } from '../_shared/util.component';
import { Token } from '../_dto/Token.Dto';


@Injectable()
export class AuthGuardService implements CanActivate {
    constructor(public jwtHelper: JwtHelperService, public router: Router, public utilComponent: UtilComponent) { }

    canActivate(): boolean {
        let token: Token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));
        if (token == null) {
            this.router.navigate(['login']);
            return false;
        }
        else if (this.jwtHelper.isTokenExpired(token.access_token)) {
            this.router.navigate(['login']);
            return false;
        }
        return true;
    }

}