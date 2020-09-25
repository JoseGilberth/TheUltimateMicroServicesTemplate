import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { OcrDTO } from '../../../_dto/ocr/Ocr.Dto';
import { OcrService } from '../../../_servicios/ocr/ocr.service';
import { UtilComponent } from '../../../_shared/util.component';
import { UtilCanvasComponent } from '../../../_shared/utilCanvas.component';
import { UtilOcrComponent } from '../../../_shared/utilOCR.component';

@Component({
  templateUrl: 'ocr.component.html',
  styleUrls: ['ocr.component.css']
})
export class OcrComponent {


  @ViewChild("video") public video: ElementRef;
  @ViewChild("canvas") public canvas: ElementRef;
  @ViewChild("IFE") public IFE: ElementRef;
  @ViewChild("datosIFE") public datosIFE: ElementRef;

  private context: CanvasRenderingContext2D;
  devices: any[] = [];
  metadataIFE: { x: number, y: number, w: number, h: number };
  metadataDatosIFE: { x: number, y: number, w: number, h: number };

  contraste: number = 100;
  brillo: number = 10;
  datos: string[] = [];
  nombre: string = "";
  domicilio: string = "";
  cveElector: string = "";
  curp: string = "";

  constructor(private ocrService: OcrService
    , private utilCanvasComponent: UtilCanvasComponent
    , private utilComponent: UtilComponent
    , private utilOcrComponent: UtilOcrComponent) {
  }

  ngOnInit() {

  }

  ngAfterViewInit() {
    navigator.mediaDevices.enumerateDevices().then(data => {
      this.obtenerDispositivos(data);
    });
    //{ video: true }
  }

  obtenerDispositivos(deviceInfos) {
    let _this = this;
    for (let i = 0; i !== deviceInfos.length; ++i) {
      const deviceInfo = deviceInfos[i];
      if (deviceInfo.kind === 'videoinput') {
        _this.devices.push({ id: deviceInfo.deviceId, label: deviceInfo.label });
      } else {
        console.log('Found another kind of device: ', deviceInfo);
      }
    }
    this.onSelectDevice(this.devices[0].id);
  }

  onSelectDevice(deviceId: string) {
    this.getStream(deviceId);
  }

  getStream(deviceId: string) {
    const hdConstraints = {
      deviceId: { exact: deviceId },
      video: {
        width: { min: 1280 },
        height: { min: 720 }
      }
    };
    navigator.mediaDevices.getUserMedia(hdConstraints).then(stream => {
      this.video.nativeElement.srcObject = stream;
      this.video.nativeElement.play();
      this.video.nativeElement.onloadedmetadata = () => {
        let ratio = this.video.nativeElement.videoWidth / this.video.nativeElement.videoHeight;
        let w = this.video.nativeElement.videoWidth - 100;
        let h = (w / ratio);
        this.drawFrame(this.video, this.canvas, w, h);
      }
    });
  }

  drawFrame(video: ElementRef, canvas: ElementRef, w: number, h: number) {
    let _this = this;
    this.utilCanvasComponent.drawFrame(video, canvas, w, h);
    let imageData = this.utilCanvasComponent.getImageData(canvas, w, h);
    this.utilCanvasComponent.grayScale(imageData.data);
    this.utilCanvasComponent.putImageData(canvas, imageData);
    this.metadataIFE = this.utilCanvasComponent.drawRectangleCentered(canvas, 70, 80, w, h, 7, 'red');
    this.metadataDatosIFE = this.utilCanvasComponent.drawRectangle(canvas, this.metadataIFE, 70, 70, 30, 20, this.metadataIFE.w, this.metadataIFE.h, 7, 'red', false);
    this.utilCanvasComponent.drawRectangle(canvas, this.metadataDatosIFE, 35, 40, 65, 0, this.metadataDatosIFE.w, this.metadataDatosIFE.h, 7, 'red', true);
    // REPETIR
    setTimeout(function () {
      _this.drawFrame(video, canvas, w, h);
    }, 10);
  }


  capture() {
    this.context = this.canvas.nativeElement.getContext("2d");
    var imageData = this.context.getImageData(this.metadataIFE.x, this.metadataIFE.y, this.metadataIFE.w, this.metadataIFE.h);
    this.context = this.IFE.nativeElement.getContext("2d");
    this.IFE.nativeElement.width = this.metadataIFE.w;
    this.IFE.nativeElement.height = this.metadataIFE.h;
    this.context.putImageData(imageData, 0, 0);
    this.getData();
  }

  getData() {
    this.context = this.canvas.nativeElement.getContext("2d");
    var imageData = this.context.getImageData(this.metadataDatosIFE.x, this.metadataDatosIFE.y, this.metadataDatosIFE.w, this.metadataDatosIFE.h);

    this.context = this.datosIFE.nativeElement.getContext("2d");
    this.datosIFE.nativeElement.width = this.metadataDatosIFE.w;
    this.datosIFE.nativeElement.height = this.metadataDatosIFE.h;
    this.context.putImageData(imageData, 0, 0);

    let base64 = this.datosIFE.nativeElement.toDataURL("image/jpeg").split("data:image/jpeg;base64,")[1];
    let ocrDTO = new OcrDTO();
    ocrDTO.archivo = base64;
    this.ocrService.post(ocrDTO)
      .subscribe(resp => {
        this.datos = resp.cuerpo.split("\n");
        console.log(this.datos);
        this.datos = this.utilComponent.limpiaArreglo(this.datos)
        this.datos = this.utilComponent.upperCaseArreglo(this.datos)
        console.log(this.datos);

        this.domicilio = this.utilOcrComponent.obtenerDomicilio(this.datos);
        this.nombre = this.utilOcrComponent.obtenerNombre(this.datos);
        this.cveElector = this.utilOcrComponent.obtenerCampoPorLongitudYOrden(this.datos, 8, 1);
        this.curp = this.utilOcrComponent.obtenerCampoPorLongitudYOrden(this.datos, 8, 2);

      }, (error: HttpErrorResponse) => {
      });
  }




}
