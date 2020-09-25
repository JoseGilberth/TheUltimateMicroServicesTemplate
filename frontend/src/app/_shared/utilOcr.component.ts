import { Injectable, OnInit } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilOcrComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }


  obtenerCampoPorLongitudYOrden(datos: string[], longitud: number, orden: number): string {
    let dato = "";
    let contador = 0;
    datos.forEach(data => {
      let datosSeccionados = data.split(" ");
      datosSeccionados.forEach(seccion => {
        if (seccion.length >= longitud) {
          if (/\d/.test(seccion)) {
            contador++;
            if (orden == contador) {
              return dato = seccion;
            }
          }
        }
      });
    });
    return dato;
  }


  obtenerDomicilio(datos: string[]): string {
    let domicilio = "";
    let contadorArreglo = 0;
    let contadorNombre = -1;
    let contadorDomicilio = -1;
    let contadorClave = -1;

    datos.forEach(data => {
      if (data.includes("NOMBRE")) {
        contadorNombre = contadorArreglo;
        console.log("contadorNombre: " + contadorNombre);
      }
      if (data.includes("DOMICILIO")) {
        contadorDomicilio = contadorArreglo;
        console.log("contadorDomicilio: " + contadorDomicilio);
      }
      if (data.includes("CLAVE") || data.includes("ELECTOR")) {
        contadorClave = contadorArreglo;
        console.log("contadorClave: " + contadorClave);
      }
      contadorArreglo++;
    });

    if (contadorNombre == -1 && contadorDomicilio == -1 && contadorClave == -1) {// NO HAY NOMBRE NO  HAY DOMICILIO NO HAY CLAVE
      // 0 - 0 - 0
      console.log("NO HAY NOMBRE NO  HAY DOMICILIO NO HAY CLAVE: ");
      domicilio = "DATOS NO ENCONTRADOS";

    } else if (contadorNombre == -1 && contadorDomicilio == -1 && contadorClave != -1) { // NO HAY NO NOMBRE NO HAY DOMICILIO HAY CLAVE
      // 0 - 0 - 1
      console.log("NO HAY NO NOMBRE NO HAY DOMICILIO HAY CLAVE:");
      domicilio = this.fillData(datos, contadorClave - 4, contadorClave);

    } else if (contadorNombre == -1 && contadorDomicilio != -1 && contadorClave == -1) { //NO HAY NOMBRE HAY DOMICILIO NO HAY CLAVE
      // 0 - 1 - 0
      console.log("NO HAY NOMBRE HAY DOMICILIO NO HAY CLAVE: ");
      domicilio = this.fillData(datos, contadorDomicilio, contadorDomicilio + 3);

    } else if (contadorNombre != -1 && contadorDomicilio == -1 && contadorClave == -1) { // HAY NOMBRE NO HAY DOMICILIO NO HAY CLAVE
      // 1 - 0 - 0
      console.log("HAY NOMBRE NO HAY DOMICILIO NO HAY CLAVE: ");
      domicilio = this.fillData(datos, contadorNombre + 3, contadorNombre + 6);

    } else if (contadorNombre != -1 && contadorDomicilio == -1 && contadorClave != -1) { // HAY NOMBRE NO HAY DOMICILIO HAY CLAVE
      // 1 - 0 - 1
      console.log(" HAY NOMBRE NO HAY DOMICILIO HAY CLAVE: ");
      domicilio = this.fillData(datos, contadorClave - 4, contadorClave);

    } else if (contadorNombre != -1 && contadorDomicilio != -1 && contadorClave == -1) { // HAY NOMBRE HAY DOMICILIO NO HAY CLAVE
      // 1 - 1 - 0
      console.log("HAY NOMBRE HAY DOMICILIO NO HAY CLAVE: ");
      domicilio = this.fillData(datos, contadorDomicilio, contadorDomicilio + 4);

    } else { // HAY NOMBRE HAY DOMICILIO NO HAY CLAVE
      // 1 - 1 - 1
      console.log("HAY NOMBRE HAY DOMICILIO HAY CLAVE: ");
      domicilio = this.fillData(datos, contadorDomicilio, contadorClave);
    }
    return domicilio;
  }

  obtenerNombre(datos: string[]): string {
    let nombre = "";
    let contadorArreglo = 0;
    let contadorNombre = -1;
    let contadorDomicilio = -1;

    datos.forEach(data => {
      if (data.includes("NOMBRE")) {
        contadorNombre = contadorArreglo;
        console.log("contadorNombre: " + contadorNombre);
      }
      if (data.includes("DOMICILIO")) {
        contadorDomicilio = contadorArreglo;
        console.log("contadorDomicilio: " + contadorDomicilio);
      }
      contadorArreglo++;
    });
    if (contadorDomicilio != -1 && contadorNombre != -1) {// HAY DOMICILIO Y NOMBRE
      console.log(" HAY DOMICILIO Y NOMBRE: " + contadorNombre + "   " + contadorDomicilio);
      nombre = this.fillData(datos, contadorNombre, contadorDomicilio);
    } else if (contadorDomicilio != -1 && contadorNombre == -1) { // HAY DOMICILIO PERO NOMBRE NO
      console.log("HAY DOMICILIO PERO NOMBRE NO: " + (contadorDomicilio - 3) + "   " + contadorDomicilio);
      nombre = this.fillData(datos, contadorDomicilio - 4, contadorDomicilio);
    } else if (contadorDomicilio == -1 && contadorNombre != -1) { // NO HAY DOMICILIO PERO NOMBRE SI
      console.log("NO HAY DOMICILIO PERO NOMBRE SI: " + contadorNombre + "   " + (contadorDomicilio + 3));
      nombre = this.fillData(datos, contadorNombre, contadorDomicilio + 3);
    } else if (contadorDomicilio == -1 && contadorNombre == -1) { // NO HAY DOMICILIO NI NOMBRE
      nombre = "NO ENCONTRADO";
    }
    return nombre;
  }

  fillData(datos: string[], contadorInicial: number, contadorFinal: number): string {
    let pivote: string = "";
    let pivoteContadorInicial = contadorInicial;// 1 
    while (pivoteContadorInicial < contadorFinal - 1) {// 1 al 6 , 2-3-4-5
      console.log(pivoteContadorInicial + " " + datos[pivoteContadorInicial + 1]); // 2  COLIN
      pivote += datos[pivoteContadorInicial + 1] + " ";
      pivoteContadorInicial++;
    }
    return pivote;
  }

}
