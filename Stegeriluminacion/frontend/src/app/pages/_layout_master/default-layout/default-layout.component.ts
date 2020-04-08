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
    , public webSocketAPI: WebSocketAPI) {

  }

  toggleMinimize(e) {
    this.sidebarMinimized = e;
  }

  desconectar() {
    this.webSocketAPI._disconnect();
  }

  conectar() {
    this.webSocketAPI.connect();
  }

  cerrarSesion() {
    this.utilComponent.showSweetAlertLoading("Cerrar Sesión", "Cerrando sesion");
    this.loginService.cerrarSesion()
      .subscribe(resp => {
        this.webSocketAPI._disconnect();
        localStorage.clear();
        this.utilComponent.showSweetAlert("Ok", "Sesión cerrada", "success");
        this.router.navigate(['login']);
      }, (error: HttpErrorResponse) => {
        this.utilComponent.showSweetAlert("Error", this.utilComponent.trataErrores(error), "error");
      });
  }

}
