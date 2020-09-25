import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { configuraciones } from '../../../environments/configuraciones';
import { LoginDTO } from '../../_dto/login/Login.Dto';
import { LoginService } from '../../_servicios/login.service';
import { UtilComponent } from '../../_shared/util.component';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { FingerPrintLoginComponent } from './fingerprint/fingerprint.login.component';
import { Subject } from 'rxjs';
import { RequestOfLoginService } from '../../_servicios/requestoflogin.service';
import { RequestOfLoginDTO } from '../../_dto/login/RequestOfLogin.Dto';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-dashboard',
  templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit {

  public loginDTO: LoginDTO;
  public loginFG: FormGroup;
  timer: any;

  bsModalRef: BsModalRef;
  @Output() callbackOnModelWindowClose: EventEmitter<null> = new EventEmitter();

  constructor(
    private formBuilder: FormBuilder
    , private loginService: LoginService
    , public router: Router
    , public utilComponent: UtilComponent
    , private modalService: BsModalService
    , private requestOfLoginService: RequestOfLoginService) {

    this.loginFG = this.formBuilder.group({
      usuario: ['', Validators.required],
      password: ['', Validators.required]
    });
  }


  ngOnInit() {
    this.loginDTO = new LoginDTO();
    localStorage.clear();
    sessionStorage.clear();
  }

  iniciarSesion() {
    this.loginDTO.huella = null;
    this.utilComponent.showSweetAlertLoading("Iniciar Sesión", "Iniciando sesion");
    this.loginService.iniciarSesion(this.loginDTO).subscribe(
      respuesta => {
        let token = respuesta;
        localStorage.setItem(configuraciones.static.token, JSON.stringify(token));
        this.router.navigate(['dashboard']);
        this.utilComponent.showSweetAlert("Bienvenido", "", "success");
      },
      (error: HttpErrorResponse) => {
        this.utilComponent.showSweetAlert("Error", this.utilComponent.trataErrores(error), "error");
      });
  }

  openFingerPrintLogin() {
    const modalOptions = {
      initialState: {
      },
      class: 'modal-lg'
    };
    this.bsModalRef = this.modalService.show(FingerPrintLoginComponent, modalOptions);
    this.bsModalRef.content.onClose = new Subject<boolean>();
    this.bsModalRef.content.onClose.subscribe((result: boolean) => {
      if (result) {
      }
    });
  }

  openFingerPrintLogin2() {
    this.utilComponent.showSweetAlertLoading("Iniciar Sesión", "Iniciando sesion");
    this.requestOfLoginService.crear(this.loginDTO.username).subscribe(
      respuesta => {
        let resp: RequestOfLoginDTO = respuesta.cuerpo;
        window.location.href = 'smartdevelop://loginFPService?usuario=' + this.loginDTO.username
          + "&basic=" + configuraciones.auth.basic
          + "&uuid=" + resp.uuid
          + '&callback=' + environment.host + "/" + environment.micro_auth.post.iniciarSesion;
        this.timer = setInterval(() => { this.checkIfToken(resp.uuid) }, 1 * 1000);
      },
      (error: HttpErrorResponse) => {
        this.utilComponent.showSweetAlert("Error", this.utilComponent.trataErrores(error), "error");
      });
  }

  checkIfToken(uuid: string) {
    this.requestOfLoginService.obtenerUuid(uuid).subscribe(
      respuesta => {
        let resp: RequestOfLoginDTO = respuesta.cuerpo;
        console.log("es nuelo " + (resp.access_token != null));
        if (resp.access_token != null) {
          console.log("clearInterval");
          clearInterval(this.timer);
          localStorage.setItem(configuraciones.static.token, JSON.stringify(resp));
          this.utilComponent.showSweetAlert("Bienvenido", "", "success");
          this.router.navigate(['dashboard']);
        } else {
          console.log("null");
        }
      },
      (error: HttpErrorResponse) => {
        this.utilComponent.showSweetAlert("Error", this.utilComponent.trataErrores(error), "error");
      });
  }


}
