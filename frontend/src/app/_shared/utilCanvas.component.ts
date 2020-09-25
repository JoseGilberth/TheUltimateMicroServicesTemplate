import { Injectable, OnInit, ElementRef } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilCanvasComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }

  // DIBUJAR UNA IMAGEN EN UN CANVAS
  drawFrame(image: ElementRef, to: ElementRef, w: number, h: number) {
    // CONTEXT TOMANDO CANVAS
    let context: CanvasRenderingContext2D = to.nativeElement.getContext('2d');
    to.nativeElement.width = w;
    to.nativeElement.height = h;
    // DRAW IMAGE TO CANVAS
    context.drawImage(image.nativeElement, 0, 0, w, h);
  }

  // OBTENER DATA DE UNA IMAGEN
  getImageData(toExtractImageData: ElementRef, w: number, h: number) {
    let context: CanvasRenderingContext2D = toExtractImageData.nativeElement.getContext('2d');
    return context.getImageData(0, 0, w, h);
  }

  // PONER DATA MODIFICADA A UNA IMAGEN
  putImageData(toPutImageData: ElementRef, imageData: ImageData) {
    let context: CanvasRenderingContext2D = toPutImageData.nativeElement.getContext('2d');
    context.putImageData(imageData, 0, 0);
  }


  drawRectangleCentered(toSetStroke: ElementRef, widthPercentage: number, heightPercentage: number
    , w: number, h: number, lineWidth: number, strokeStyle: string): { x: number, y: number, w: number, h: number } {

    let x = ((((100 - widthPercentage) / 2) * w) / 100);
    let y = ((((100 - heightPercentage) / 2) * h) / 100);
    w = ((widthPercentage * w) / 100);
    h = ((heightPercentage * h) / 100);
    let context: CanvasRenderingContext2D = toSetStroke.nativeElement.getContext('2d');
    context.beginPath();
    context.rect(x, y, w, h);
    context.lineWidth = lineWidth;
    context.strokeStyle = strokeStyle;
    context.stroke();
    return { x: x, y: y, w: w, h: h };
  }

  drawRectangle(toSetStroke: ElementRef, metadata: any, widthPercentage: number, heightPercentage: number, x: number, y: number
    , w: number, h: number, lineWidth: number, strokeStyle: string, fill: boolean): { x: number, y: number, w: number, h: number } {
    let x1 = metadata.x;
    let y1 = metadata.y;
    x1 += ((x * w) / 100);
    y1 += ((y * h) / 100);
    w = ((widthPercentage * w) / 100);
    h = ((heightPercentage * h) / 100);
    let context: CanvasRenderingContext2D = toSetStroke.nativeElement.getContext('2d');
    context.beginPath();
    context.rect(x1, y1, w, h);
    if (fill) {
      context.fillStyle = strokeStyle;
      context.fill();
    }
    context.lineWidth = lineWidth;
    context.strokeStyle = strokeStyle;
    context.stroke();
    return { x: x1, y: y1, w: w, h: h };
  }


  //CONVERTIR A ESCALA DE GRISES
  grayScale(data) {
    // (R + G + B) ÷ 3
    for (var i = 0; i < data.length; i += 4) {
      let red = data[i];
      let green = data[i + 1];
      let blue = data[i + 2];
      data[i] = (red + green + blue) / 3; // Red
      data[i + 1] = (red + green + blue) / 3; // Green
      data[i + 2] = (red + green + blue) / 3; // Blue
    }
    return data;
  }

  // BRILLO A UNA IMAGEN CANVAS
  applyBrightness(data, brightness) {
    for (var i = 0; i < data.length; i += 4) {
      data[i] += 255 * (brightness / 100);
      data[i + 1] += 255 * (brightness / 100);
      data[i + 2] += 255 * (brightness / 100);
    }
    return data;
  }

  // CONTRASTE A UNA IMAGEN CANVAS
  applyContrast(data, contrast) {
    var factor = (259.0 * (contrast + 255.0)) / (255.0 * (259.0 - contrast));
    for (var i = 0; i < data.length; i += 4) {
      data[i] = this.truncateColor(factor * (data[i] - 128.0) + 128.0);
      data[i + 1] = this.truncateColor(factor * (data[i + 1] - 128.0) + 128.0);
      data[i + 2] = this.truncateColor(factor * (data[i + 2] - 128.0) + 128.0);
    }
    return data;
  }

  truncateColor(value) {
    if (value < 0) {
      value = 0;
    } else if (value > 255) {
      value = 255;
    }
    return value;
  }


}
