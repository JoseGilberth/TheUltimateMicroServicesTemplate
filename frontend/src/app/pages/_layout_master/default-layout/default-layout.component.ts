import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { navItems } from '../../../_nav';
import { LoginService } from '../../../_servicios/login.service';
import { UtilComponent } from '../../../_shared/util.component';
import { WebSocketAPI } from '../../../_shared/websocketapi.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html'
})
export class DefaultLayoutComponent implements OnInit {

  public sidebarMinimized = false;
  public navItems = [];

  constructor(private loginService: LoginService
    , private utilComponent: UtilComponent
    , public router: Router
    , public webSocketAPI: WebSocketAPI) {
  }

  ngOnInit(): void {
    navItems.forEach((navItem: any) => {
      if (navItem.title) {
        this.navItems.push(navItem);
      }
      if (this.utilComponent.validaPermiso(navItem.permiso) || navItem.permiso == 'theme') {
        this.navItems.push(navItem);
      }
    });

  }

  toggleMinimize(e) {
    this.sidebarMinimized = e;
  }

  desconectar() {
    this.webSocketAPI._disconnect();
  }

  conectar() {
    this.webSocketAPI.initConection();
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
