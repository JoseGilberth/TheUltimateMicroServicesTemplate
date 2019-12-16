import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UtilComponent } from '../_shared/util.component';

@Injectable()
export class PermissionGuardService implements CanActivate {

    constructor(public jwtHelper: JwtHelperService
        , public router: Router
        , public utilComponent: UtilComponent) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        let hasPermission: boolean = true;
        if (route.data.permiso != undefined) {
            if (this.utilComponent.validaPermiso(route.data.permiso)) {
                return true;
            } else {
                this.router.navigate(['/403']);
                return false;
            }
        }
        return hasPermission;
    }




}