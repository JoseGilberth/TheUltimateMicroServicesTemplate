import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { NotificationComponent } from '../_shared/notification.component';
import { UtilComponent } from '../_shared/util.component';

@Injectable()
export class PermissionGuardService implements CanActivate {

    constructor(public jwtHelper: JwtHelperService, public router: Router, public utilComponent: UtilComponent, public notificacion: NotificationComponent) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        let hasPermission: boolean = true;
        /*
        route.children.forEach(child => {
            if (child.data.permiso != undefined) {
                console.log("PERMISO: " + child.data.permiso);
                if (this.utilComponent.hasPermiso(child.data.permiso)) {
                    console.log("TIENE PERMISO");
                    return true;
                } else {
                    console.log("NO TIENE PERMISO");
                    this.notificacion.showNotification("Error" + " : " + "No cuenta con los permisos suficientes para ingresar a este recurso", 'top', 'right', 'danger');
                    this.router.navigate(['/dashboard']);
                    return false;
                }
            }
        });
        */
        return hasPermission;
    }


}