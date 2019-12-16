import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginDTO } from '../../_dto/login/Login.Dto';
import { LoginService } from '../../_servicios/login.service';
import { UtilComponent } from '../../_shared/util.component';
import { HttpErrorResponse } from '@angular/common/http';
import { configuraciones } from '../../../environments/configuraciones';

@Component({
  selector: 'app-dashboard',
  templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit {

  public loginDTO: LoginDTO;
  public loginFG: FormGroup;

  constructor(
    private formBuilder: FormBuilder
    , private loginService: LoginService
    , public router: Router
    , public utilComponent: UtilComponent) {

    this.loginFG = this.formBuilder.group({
      usuario: ['', Validators.required],
      password: ['', Validators.compose([Validators.required, Validators.minLength(6)])],
    });
  }

  ngOnInit() {
    this.loginDTO = new LoginDTO();
  }

  iniciarSesion() {
    this.utilComponent.showSweetAlertLoading("Iniciar Sesión", "Iniciando sesion");
    this.loginService.iniciarSesion(this.loginDTO).subscribe(
      respuesta => {
        let token = respuesta;
        localStorage.setItem(configuraciones.static.token, JSON.stringify(token));
         this.router.navigate(['dashboard']);
         this.utilComponent.showSweetAlert("Bienvenido", "", "success"); 
      },
      (error: HttpErrorResponse) => {
        console.log("ERROR: " + JSON.stringify(error));
      });
  }

}
