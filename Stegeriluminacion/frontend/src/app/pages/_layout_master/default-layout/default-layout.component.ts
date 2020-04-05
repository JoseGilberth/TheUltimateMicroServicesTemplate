import { Component } from '@angular/core';
import { navItems } from '../../../_nav';
import { LoginService } from '../../../_servicios/login.service';
import { HttpErrorResponse } from '@angular/common/http';
import { UtilComponent } from '../../../_shared/util.component';
import { Router } from '@angular/router';
import { WebSocketAPI } from '../../../_shared/websocketapi.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html'
})
export class DefaultLayoutComponent {

  public sidebarMinimized = false;
  public navItems = navItems;

  constructor(private loginService: LoginService
    , private utilComponent: UtilComponent
    , public router: Router
    , private webSocketAPI: WebSocketAPI) {

  }

  toggleMinimize(e) {
    this.sidebarMinimized = e;
  }

  cerrarSesion() {
    this.webSocketAPI._disconnect();
    this.utilComponent.showSweetAlertLoading("Cerrar Sesión", "Cerrando sesion");
    this.loginService.cerrarSesion()
      .subscribe(resp => {
        localStorage.clear();
        this.utilComponent.showSweetAlert("Ok", "Sesión cerrada", "success");
        this.router.navigate(['login']);
      }, (error: HttpErrorResponse) => {
        this.utilComponent.showSweetAlert("Error", this.utilComponent.trataErrores(error), "error");
      });
  }

}
