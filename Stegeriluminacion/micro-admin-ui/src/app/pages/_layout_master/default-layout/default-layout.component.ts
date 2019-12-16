import { Component } from '@angular/core';
import { navItems } from '../../../_nav';
import { LoginService } from '../../../_servicios/login.service';
import { HttpErrorResponse } from '@angular/common/http';
import { UtilComponent } from '../../../_shared/util.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html'
})
export class DefaultLayoutComponent {

  public sidebarMinimized = false;
  public navItems = navItems;

  constructor(private loginService: LoginService
    , private utilComponent: UtilComponent
    , public router: Router) {

  }

  toggleMinimize(e) {
    this.sidebarMinimized = e;
  }

  cerrarSesion() {
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
