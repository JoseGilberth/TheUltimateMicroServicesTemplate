import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { configuraciones } from '../../../../environments/configuraciones';
import { LoginDTO } from '../../../_dto/login/Login.Dto';
import { LoginService } from '../../../_servicios/login.service';
import { UtilComponent } from '../../../_shared/util.component';
import Swal from 'sweetalert2';


@Component({
  templateUrl: 'fingerprint.login.component.html'
})
export class FingerPrintLoginComponent {

  onClose: Subject<boolean>;
  reader: any = null;
  currentFormat = Fingerprint.SampleFormat.PngImage;
  fingerPrintSelected: any; // Drop down selected value of reader  
  acquisitionStarted: boolean = false;
  operationToRestart: any = null;
  quality: number;
  sdk: any
  public loginDTO: LoginDTO;
  statusHuellero: string = "";
  isLoadingHuellero: boolean = false;

  constructor(public bsModalRef: BsModalRef
    , private loginService: LoginService
    , public router: Router
    , public utilComponent: UtilComponent) {
  }

  public ngOnInit(): void {
    this.onClose = new Subject();
    this.onLoad();
    this.loginDTO = new LoginDTO();
    this.loginDTO.username = "";
    this.loginDTO.password = "";
    this.loginDTO.huella = "";
  }


  FingerprintSdkLogin: any = (function () {
    function FingerprintSdkLogin(utilComponent, _this) {
      this.operationToRestart = null;
      this.acquisitionStarted = false;
      this.sdk = new Fingerprint.WebApi;
      let contador = 0;
      this.sdk.onDeviceConnected = function (e) {// Detects if the deveice is connected for which acquisition started
        console.log("Scan your finger");
      };
      this.sdk.onDeviceDisconnected = function (e) {// Detects if device gets disconnected - provides deviceUid of disconnected device
        console.log("Device disconnected");
      };
      this.sdk.onCommunicationFailed = function (e) {// Detects if there is a failure in communicating with U.R.U web SDK
        if (contador >= 3) {
          return;
        }
        _this.isLoadingHuellero = true;
        _this.statusHuellero = "No se puede establecer una conexión entre el huellero y la PC, asegurece de tener un lector de huellas conectado";
        contador++;
      };
      this.sdk.onSamplesAcquired = function (s) { // Sample acquired event triggers this function  
        if (_this.loginDTO.username == "") {
          _this.statusHuellero = "Debes indicar un nombre de usuario";
          _this.isLoadingHuellero = true;
          Swal.close();
        } else {
          _this.sampleAcquired(s);
        }
      };
      this.sdk.onQualityReported = function (e) { // Quality of sample aquired - Function triggered on every sample acquired
        utilComponent.showSweetAlertLoading("Escaner", "Escaneando");
        _this.statusHuellero = "Calidad de la huella: " + Fingerprint.QualityCode[(e.quality)];
      }
    }

    FingerprintSdkLogin.prototype.startCapture = function (fingerPrintSelected) {
      let _this = this;
      if (this.acquisitionStarted) // Monitoring if already started capturing
        return;
      this.operationToRestart = this.startCapture;
      this.sdk.startAcquisition(Fingerprint.SampleFormat.PngImage, fingerPrintSelected).then(function () {
        _this.acquisitionStarted = true;
      }, function (error) {
      });
    };
    FingerprintSdkLogin.prototype.stopCapture = function () {
      let _this = this;
      if (!this.acquisitionStarted) //Monitor if already stopped capturing
        return;
      this.sdk.stopAcquisition().then(function () {
        _this.acquisitionStarted = false;
      }, function (error) {
      });
    };
    FingerprintSdkLogin.prototype.getInfo = function () {
      return this.sdk.enumerateDevices();
    };
    FingerprintSdkLogin.prototype.getDeviceInfoWithID = function (uid) {
      return this.sdk.getDeviceInfo(uid);
    };
    return FingerprintSdkLogin;
  })();

  /*
  ================================================================
                    HUELLERO
  ================================================================
  */
  onLoad() {
    if (this.reader != null) {
      this.reader.stopCapture();
      this.reader.sdk.webChannel.disconnect();
      this.statusHuellero = "Se ha desconectado el huellero";
    }
    this.isLoadingHuellero = true;
    this.statusHuellero = "Cargando Huellero";
    this.reader = new this.FingerprintSdkLogin(this.utilComponent, this);
    this.getFingerPrintReaders(); //To populate readers for drop down selection 
  };

  getFingerPrintReaders() {
    let _this = this;
    let allReaders = this.reader.getInfo();
    allReaders.then(function (sucessObj) {
      _this.fingerPrintSelected = sucessObj[0];
      _this.statusHuellero = "Controlador de huellero encontrado - " + _this.fingerPrintSelected;
      _this.isLoadingHuellero = false;
      if (sucessObj.length <= 0) {
        _this.isLoadingHuellero = true;
        _this.statusHuellero = "No se puede establecer una conexión entre el huellero y la PC, asegurece de tener un lector de huellas conectado e intente refrescar la página";
      } else {
        _this.reader.startCapture(_this.fingerPrintSelected);
      }
    }, function (error) {
      this.onLoad();
    });
  }

  sampleAcquired(s) {
    var samples = JSON.parse(s.samples);
    let base64 = Fingerprint.b64UrlTo64(samples[0]);
    this.loginDTO.huella = base64;
    this.iniciarSesion();
  }

  iniciarSesion() {
    this.utilComponent.showSweetAlertLoading("Iniciar Sesión", "Iniciando sesion");
    this.loginService.iniciarSesion(this.loginDTO).subscribe(
      respuesta => {
        this.reader.stopCapture();
        this.onCancel();
        let token = respuesta;
        localStorage.setItem(configuraciones.static.token, JSON.stringify(token));
        this.router.navigate(['dashboard']);
        this.utilComponent.showSweetAlertTimer("Bienvenido", "", "success", 700);
        this.bsModalRef.hide();
      },
      (error: HttpErrorResponse) => {
        this.utilComponent.showSweetAlert("Error", this.utilComponent.trataErrores(error), "error");
      });
  }

  public onCancel(): void {
    this.onClose.next(false);
    this.bsModalRef.hide();
    if (this.reader != null) {
      this.reader.stopCapture();
      this.reader.sdk.webChannel.disconnect();
      this.statusHuellero = "Se ha desconectado el huellero";
    }
  }


}
