import { Component, Input } from '@angular/core';
import { FingerPrintAuthenticationDTO } from '../../../../../_dto/usuarios/FingerPrintAuthentication.Dto';
import { FingerPrintFmdIndiceDerechoDTO } from '../../../../../_dto/usuarios/FingerPrintFmdIndiceDerecho.Dto';
import { FingerPrintFmdIndiceIzquierdoDTO } from '../../../../../_dto/usuarios/FingerPrintFmdIndiceIzquierdo.Dto';
import { FingerPrintFmdMedioDerechoDTO } from '../../../../../_dto/usuarios/FingerPrintFmdMedioDerecho.Dto';
import { FingerPrintFmdMedioIzquierdoDTO } from '../../../../../_dto/usuarios/FingerPrintFmdMedioIzquierdo.Dto';
import { FingerPrintFmdPulgarDerechoDTO } from '../../../../../_dto/usuarios/FingerPrintFmdPulgarDerecho.Dto';
import { FingerPrintFmdPulgarIzquierdoDTO } from '../../../../../_dto/usuarios/FingerPrintFmdPulgarIzquierdo.Dto';
import { UsuariosDTO } from '../../../../../_dto/usuarios/Usuarios.Dto';
import { UtilComponent } from '../../../../../_shared/util.component';


@Component({
  selector: 'fingerprint-usuario',
  styleUrls: ['fingerprint.component.css'],
  templateUrl: 'fingerprint.component.html'
})
export class FingerPublicosPrintComponent {

  @Input() usuariosDTO: UsuariosDTO;
  public fingerPrintAuthenticationDTO: FingerPrintAuthenticationDTO;

  
  isLoadingHuellero: boolean = false;
  statusHuellero: string = "";
  readerFingerPrint: any = null;
  fingerPrintSelected: any = null;// Drop down selected value of reader  
  currentFormat = Fingerprint.SampleFormat.PngImage;

  huellas: string[] = [];
  huellasIndiceDerecho: string[] = [];
  huellasMedioDerecho: string[] = [];
  huellasPulgarDerecho: string[] = [];
  huellasIndiceIzquierdo: string[] = [];
  huellasMedioIzquierdo: string[] = [];
  huellasPulgarIzquierdo: string[] = [];


  FingerprintSdkLogin: any = (function () {
    function FingerprintSdkLogin(utilComponent, _this) {
      this.operationToRestart = null;
      this.acquisitionStarted = false;
      this.sdk = new Fingerprint.WebApi;
      this.sdk.onDeviceConnected = function (e) {// Detects if the deveice is connected for which acquisition started
        console.log("Scan your finger");
      };
      this.sdk.onDeviceDisconnected = function (e) {// Detects if device gets disconnected - provides deviceUid of disconnected device 
        utilComponent.showSweetAlert("Escaner", "Conecte un dispositivo para iniciar el escaneo de huellas", "error");
      };
      this.sdk.onCommunicationFailed = function (e) {// Detects if there is a failure in communicating with U.R.U web SDK 
        console.log(JSON.stringify(e));
        _this.isLoadingHuellero = true;
        _this.statusHuellero = "No se puede establecer una conexión entre el huellero y la PC, asegurece de tener un lector de huellas conectado e intente refrescar la página";
        //utilComponent.showSweetAlert("Escaner", "No se puede establecer una conexión entre el huellero y la PC, asegurece de tener un lector de huellas conectado e intente refrescar la página", "error");
      };
      this.sdk.onSamplesAcquired = function (s) { // Sample acquired event triggers this function  
        _this.sampleAcquired(s);
      };
      this.sdk.onQualityReported = function (e) { // Quality of sample aquired - Function triggered on every sample acquired
        utilComponent.showSweetAlertLoading("Escaner", "Escaneando");
        console.log(Fingerprint.QualityCode[(e.quality)]);
        _this.quality = e.quality;
      }
    }

    FingerprintSdkLogin.prototype.startCapture = function (fingerPrintSelected) {
      let _this = this;
      if (this.acquisitionStarted) // Monitoring if already started capturing
        return;
      this.operationToRestart = this.startCapture;
      console.log("cuurren fingerPrintSelected: " + fingerPrintSelected);
      this.sdk.startAcquisition(Fingerprint.SampleFormat.PngImage, fingerPrintSelected).then(function () {
        _this.acquisitionStarted = true;
      }, function (error) {
        console.log(error.message);
      });
    };
    FingerprintSdkLogin.prototype.stopCapture = function () {
      let _this = this;
      if (!this.acquisitionStarted) //Monitor if already stopped capturing
        return;
      this.sdk.stopAcquisition().then(function () {
        _this.acquisitionStarted = false;
      }, function (error) {
        console.log(error.message);
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


  constructor(private utilComponent: UtilComponent) {
  }


  ngOnInit(): void { 
    this.usuariosDTO.fingerPrintAuthentication = new FingerPrintAuthenticationDTO();
    this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceDerecho = [];
    this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceIzquierdo = [];
    this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarDerecho = [];
    this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarIzquierdo = [];
    this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioDerecho = [];
    this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioIzquierdo = [];
    this.onLoad();
  }


  /*
  ================================================================
                     VALIDA HUELLAS
  ================================================================
  */
  validaHuellas(): boolean {
    if (this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarIzquierdo.length < 3 && this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarIzquierdo.length > 0) {
      this.utilComponent.showSweetAlert("Escaner", "La cantidad de huellas escaneadas para el dedo Pulgar Izquierdo debe ser 3", "error");
      return false;
    }
    if (this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceIzquierdo.length < 3 && this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceIzquierdo.length > 0) {
      this.utilComponent.showSweetAlert("Escaner", "La cantidad de huellas escaneadas para el dedo Indice Izquierdo debe ser 3", "error");
      return false;
    }
    if (this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioIzquierdo.length < 3 && this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioIzquierdo.length > 0) {
      this.utilComponent.showSweetAlert("Escaner", "La cantidad de huellas escaneadas para el dedo Medio Izquierdo debe ser 3", "error");
      return false;
    }
    if (this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarDerecho.length < 3 && this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarDerecho.length > 0) {
      this.utilComponent.showSweetAlert("Escaner", "La cantidad de huellas escaneadas para el dedo Pulgar Derecho debe ser 3", "error");
      return false;
    }
    if (this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceDerecho.length < 3 && this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceDerecho.length > 0) {
      this.utilComponent.showSweetAlert("Escaner", "La cantidad de huellas escaneadas para el dedo Indice Derecho debe ser 3", "error");
      return false;
    }
    if (this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioDerecho.length < 3 && this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioDerecho.length > 0) {
      this.utilComponent.showSweetAlert("Escaner", "La cantidad de huellas escaneadas para el dedo Medio Derecho debe ser 3", "error");
      return false;
    }
    return true;
  }


  /*
  ================================================================
                    HUELLERO
  ================================================================
  */
  acquisitionStarted: boolean = false;
  operationToRestart: any = null;
  numeroDedo: string = null // 0 =pulgar derecho, 1 = indice derecho , 2 = medio derecho
  seleccionEscaneo: string = "";
  quality: number;

  onLoad() {
    if (this.readerFingerPrint != null) {
      this.readerFingerPrint.stopCapture();
    }
    this.isLoadingHuellero = true;
    this.statusHuellero = "Cargando Huellero";
    this.readerFingerPrint = new this.FingerprintSdkLogin(this.utilComponent, this);
    this.getAllAvailableFingerPrintReaders(); //To populate readers for drop down selection 
  };

  getAllAvailableFingerPrintReaders() {
    let _this = this;
    let allReaders = this.readerFingerPrint.getInfo();
    allReaders.then(function (sucessObj) {
      _this.fingerPrintSelected = sucessObj[0];
      _this.statusHuellero = "Controlador de huellero encontrado - " + _this.fingerPrintSelected;
      _this.isLoadingHuellero = false;
      if (sucessObj.length <= 0) {
        _this.isLoadingHuellero = true;
        _this.statusHuellero = "No se puede establecer una conexión entre el huellero y la PC, asegurece de tener un lector de huellas conectado e intente refrescar la página";
      }
    }, function (error) {
      console.log(error.message);
      _this.statusHuellero = "Error al cargar el huellero , asegurece de tener un lector de huellas conectado e intente refrescar la página";
    });
  }

  detenerRegistroDeHuellas() {
    this.readerFingerPrint.stopCapture();
  }

  seleccionDedo(dedo: string) {
    this.numeroDedo = dedo;
    this.informacionDedos(dedo);
    this.readerFingerPrint.startCapture();
  }

  sampleAcquired(s) {
    var samples = JSON.parse(s.samples);
    let base64 = Fingerprint.b64UrlTo64(samples[0]);
    this.llenaInformacionManos(base64);
  }


  llenaInformacionManos(base64: string) {
    if (this.quality != 0) {
      this.utilComponent.showSweetAlert("Escaner", "La calidad del escaneo es mala , intenta de nuevo", "error");
      return;
    }
    let size = (base64.length * (3 / 4)) - (base64.slice(-2) == "==" ? 2 : 1);
    let mimeType = "application/png";
    let contador = 0;

    switch (this.numeroDedo) {
      case "PULGAR_DERECHO":
        contador = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarDerecho.length;
        let fingerPrintFmdPulgarDerecho = new FingerPrintFmdPulgarDerechoDTO();
        fingerPrintFmdPulgarDerecho.archivo = base64;
        fingerPrintFmdPulgarDerecho.etiqueta = "PULGAR_DERECHO_" + contador;
        fingerPrintFmdPulgarDerecho.mimeType = mimeType;
        fingerPrintFmdPulgarDerecho.tamano = size
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarDerecho.push(fingerPrintFmdPulgarDerecho);
        this.huellasPulgarDerecho.push(base64);
        break;
      case "INDICE_DERECHO":
        contador = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceDerecho.length;
        let fingerPrintFmdIndiceDerecho = new FingerPrintFmdIndiceDerechoDTO();
        fingerPrintFmdIndiceDerecho.archivo = base64;
        fingerPrintFmdIndiceDerecho.etiqueta = "INDICE_DERECHO_" + contador;
        fingerPrintFmdIndiceDerecho.mimeType = mimeType;
        fingerPrintFmdIndiceDerecho.tamano = size
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceDerecho.push(fingerPrintFmdIndiceDerecho);
        this.huellasIndiceDerecho.push(base64);
        break;
      case "MEDIO_DERECHO":
        contador = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioDerecho.length;
        let fingerPrintFmdMedioDerecho = new FingerPrintFmdMedioDerechoDTO();
        fingerPrintFmdMedioDerecho.archivo = base64;
        fingerPrintFmdMedioDerecho.etiqueta = "MEDIO_DERECHO_" + contador;
        fingerPrintFmdMedioDerecho.mimeType = mimeType;
        fingerPrintFmdMedioDerecho.tamano = size
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioDerecho.push(fingerPrintFmdMedioDerecho);
        this.huellasMedioDerecho.push(base64);
        break;
      case "PULGAR_IZQUIERDO":
        contador = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarIzquierdo.length;
        let fingerPrintFmdPulgarIzquierdo = new FingerPrintFmdPulgarIzquierdoDTO();
        fingerPrintFmdPulgarIzquierdo.archivo = base64;
        fingerPrintFmdPulgarIzquierdo.etiqueta = "PULGAR_IZQUIERDO_" + contador;
        fingerPrintFmdPulgarIzquierdo.mimeType = mimeType;
        fingerPrintFmdPulgarIzquierdo.tamano = size
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarIzquierdo.push(fingerPrintFmdPulgarIzquierdo);
        this.huellasPulgarIzquierdo.push(base64);
        break;
      case "INDICE_IZQUIERDO":
        contador = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceIzquierdo.length;
        let fingerPrintFmdIndiceIzquierdo = new FingerPrintFmdIndiceIzquierdoDTO();
        fingerPrintFmdIndiceIzquierdo.archivo = base64;
        fingerPrintFmdIndiceIzquierdo.etiqueta = "INDICE_IZQUIERDO_" + contador;
        fingerPrintFmdIndiceIzquierdo.mimeType = mimeType;
        fingerPrintFmdIndiceIzquierdo.tamano = size
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceIzquierdo.push(fingerPrintFmdIndiceIzquierdo);
        this.huellasIndiceIzquierdo.push(base64);
        break;
      case "MEDIO_IZQUIERDO":
        contador = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioIzquierdo.length;
        let fingerPrintFmdMedioIzquierdo = new FingerPrintFmdMedioIzquierdoDTO();
        fingerPrintFmdMedioIzquierdo.archivo = base64;
        fingerPrintFmdMedioIzquierdo.etiqueta = "MEDIO_IZQUIERDO_" + contador;
        fingerPrintFmdMedioIzquierdo.mimeType = mimeType;
        fingerPrintFmdMedioIzquierdo.tamano = size
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioIzquierdo.push(fingerPrintFmdMedioIzquierdo);
        this.huellasMedioIzquierdo.push(base64);
        break;
      default:
    }
    this.utilComponent.showSweetAlertTimer("Escaner", "Escaneo completado", "success", 700);
  }


  informacionDedos(dedo: string) {
    this.huellas = [];
    switch (dedo) {
      case "PULGAR_DERECHO":
        this.huellas = this.huellasPulgarDerecho;
        this.seleccionEscaneo = "Coloque el dedo pulgar derecho en el escaner";
        break;
      case "INDICE_DERECHO":
        this.huellas = this.huellasIndiceDerecho;
        this.seleccionEscaneo = "Coloque el dedo indice derecho en el escaner";
        break;
      case "MEDIO_DERECHO":
        this.huellas = this.huellasMedioDerecho;
        this.seleccionEscaneo = "Coloque el dedo medio derecho en el escaner";
        break;
      case "PULGAR_IZQUIERDO":
        this.huellas = this.huellasPulgarIzquierdo;
        this.seleccionEscaneo = "Coloque el dedo pulgar izquierdo en el escaner";
        break;
      case "INDICE_IZQUIERDO":
        this.huellas = this.huellasIndiceIzquierdo;
        this.seleccionEscaneo = "Coloque el dedo indice izquierdo en el escaner";
        break;
      case "MEDIO_IZQUIERDO":
        this.huellas = this.huellasMedioIzquierdo;
        this.seleccionEscaneo = "Coloque el dedo medio izquierdo en el escaner";
        break;
      default:
    }
  }

  reiniciaDedo(dedo: string) {
    this.huellas = [];
    switch (dedo) {
      case "PULGAR_DERECHO":
        this.huellasPulgarDerecho = [];
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarDerecho = [];
        break;
      case "INDICE_DERECHO":
        this.huellasIndiceDerecho = [];
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceDerecho = [];
        break;
      case "MEDIO_DERECHO":
        this.huellasMedioDerecho = [];
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioDerecho = [];
        break;
      case "PULGAR_IZQUIERDO":
        this.huellasPulgarIzquierdo = [];
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarIzquierdo = [];
        break;
      case "INDICE_IZQUIERDO":
        this.huellasIndiceIzquierdo = [];
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceIzquierdo = [];
        break;
      case "MEDIO_IZQUIERDO":
        this.huellasMedioIzquierdo = [];
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioIzquierdo = [];
        break;
      default:
    }
  }

  eliminaEscaneo(huella: any) {
    switch (this.numeroDedo) {
      case "PULGAR_DERECHO":
        var index1 = this.huellasPulgarDerecho.findIndex(item => item == huella);
        var index2 = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarDerecho.findIndex(item => item.archivo == huella);
        this.huellasPulgarDerecho.splice(index1, 1);
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarDerecho.splice(index2, 1);
        break;
      case "INDICE_DERECHO":
        var index1 = this.huellasIndiceDerecho.findIndex(item => item == huella);
        var index2 = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceDerecho.findIndex(item => item.archivo == huella);
        this.huellasIndiceDerecho.splice(index1, 1);
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdIndiceDerecho.splice(index2, 1);
        break;
      case "MEDIO_DERECHO":
        var index1 = this.huellasMedioDerecho.findIndex(item => item == huella);
        var index2 = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioDerecho.findIndex(item => item.archivo == huella);
        this.huellasMedioDerecho.splice(index1, 1);
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioDerecho.splice(index2, 1);
        break;
      case "PULGAR_IZQUIERDO":
        var index1 = this.huellasPulgarIzquierdo.findIndex(item => item == huella);
        var index2 = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarIzquierdo.findIndex(item => item.archivo == huella);
        this.huellasPulgarIzquierdo.splice(index1, 1);
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarIzquierdo.splice(index2, 1);
        break;
      case "INDICE_IZQUIERDO":
        var index1 = this.huellasPulgarDerecho.findIndex(item => item == huella);
        var index2 = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarDerecho.findIndex(item => item.archivo == huella);
        this.huellasPulgarDerecho.splice(index1, 1);
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdPulgarDerecho.splice(index2, 1);
        break;
      case "MEDIO_IZQUIERDO":
        var index1 = this.huellasMedioIzquierdo.findIndex(item => item == huella);
        var index2 = this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioIzquierdo.findIndex(item => item.archivo == huella);
        this.huellasMedioIzquierdo.splice(index1, 1);
        this.usuariosDTO.fingerPrintAuthentication.fingerPrintFmdMedioIzquierdo.splice(index2, 1);
        break;
      default:
    }

  }

}
