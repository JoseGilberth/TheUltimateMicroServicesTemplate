import { OnInit, Injectable } from '@angular/core';
import { ItemNode } from './arbol/ItemNode';
import { configuraciones } from '../../environments/configuraciones';
import Swal, { SweetAlertIcon } from 'sweetalert2';
import { PermisoPublicoDTO } from '../_dto/usuarios/PermisoPublico.Dto';
import { formatDate } from '@angular/common';
import { TreeviewItem, TreeItem } from 'ngx-treeview';
import { ToastrService } from "ngx-toastr";
import { HttpErrorResponse } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { TokenDecoded } from '../_dto/login/TokenDecoded.Dto';

declare const $: any;


@Injectable({
  providedIn: 'root'
})
export class UtilComponent implements OnInit {

  loading: boolean = false;

  constructor(private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  obtenerTipoUsuario() : string { const helper = new JwtHelperService();
    let token = localStorage.getItem(configuraciones.static.token);
    let decodedToken = <TokenDecoded>helper.decodeToken(token);
    return decodedToken.TipoUsuario;
  }


  validaPermiso(authorityToValidate: string): boolean {
    let valido: boolean = false;
    const helper = new JwtHelperService();
    let token = localStorage.getItem(configuraciones.static.token);
    let decodedToken = <TokenDecoded>helper.decodeToken(token);
    //const expirationDate = helper.getTokenExpirationDate(token);
    //const isExpired = helper.isTokenExpired(token);
    if (decodedToken.authorities != null) {
      let indice = decodedToken.authorities.findIndex(authority => authority == authorityToValidate);
      if (indice != -1) {
        valido = true;
      }
    }
    return valido;
  }

  trataErrores(error: HttpErrorResponse): string {
    let errores: string = "";
    if (error.error != null) {
      if (error.error.cuerpo != null) {
        if (error.error.codigo == 403) {
          this.presentToasterError(String(error.error.mensaje));
        }
        if (error.error.cuerpo.lista != null) {
          let cadenas = JSON.parse(JSON.stringify(error.error.cuerpo.lista));
          for (let value of Object.values(cadenas)) {
            this.presentToasterInfo(String(value));
            errores += value + "<br>";
          }
          return errores;
        } else {
          errores = error.error.mensaje;
          return errores;
        }
      } else {
        errores = error.error.mensaje;
        return errores;
      }
    }
  }

  presentToasterInfo(mensaje: string) {
    this.toastr.info(mensaje, "", {
      timeOut: 5000
    });
  }

  presentToasterSuccess(mensaje: string) {
    this.toastr.success(mensaje, "", {
      timeOut: 5000
    });
  }

  presentToasterWarning(mensaje: string) {
    this.toastr.warning(mensaje, "", {
      timeOut: 5000
    });
  }

  presentToasterError(mensaje: string) {
    this.toastr.error(mensaje, "", {
      timeOut: 5000
    });
  }

  showSweetAlertLoading(titulo: string, texto: string) {
    Swal.fire({
      title: titulo, text: texto, showLoaderOnConfirm: true, allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading()
      },
    });
  }

  showSweetAlert(titulo: string, texto: string, tipo: SweetAlertIcon) {
    Swal.fire({
      icon: tipo, title: titulo, html: texto, showLoaderOnConfirm: true, allowOutsideClick: false
    });
  }
  /* VALIDA SI UN VALOR SE ENCUENTRA DENTRO DE UN ARREGLO POR MEDIO DE UNA PROPIEDAD EN ESPECIFICO */
  arrayObjectIndexOf(myArray, searchTerm, property) {
    for (var i = 0, len = myArray.length; i < len; i++) {
      if (myArray[i][property] === searchTerm) return true;
    }
    return false;
  }

  /* LIMPIA LAS PROPIEDADES DE UN OBJETO */
  cleanProperties(objeto) {
    Object.keys(objeto).forEach((key, indice) => {
      if (objeto[key] != null) {// SI EL VALOR DEL OBJETO NO ES NULLO
        if ((objeto[key]).toString() === "[object Object]") { // SI EL OBJETO ES UN OBJETO
          this.cleanProperties(objeto[key]);//RECORRO PARA LIMPIAR
        } else {
          objeto[key] = null; //LIMPIO
        }
      } else { // SI ES NULLO SE VA A VACIo
        objeto[key] = null;
      }
    });
    return objeto;
  }


  /* FORMATEA LOS BYTES A UN FORMATO LEIBLE*/
  formatBytes(bytes, decimals) {
    if (bytes == 0) return '0 Bytes';
    var k = 1024,
      dm = decimals || 2,
      sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
      i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }

  /* DESCARGA UN ARCHIVO BLOB */
  blobToDownload(archivo: Blob, nombre: string) {
    var file = window.URL.createObjectURL(archivo);
    var a = document.createElement("a");
    a.href = file;
    a.download = nombre; //this.response.name || Nombre;
    document.body.appendChild(a);
    a.click();
    window.onfocus = function () {
      document.body.removeChild(a);
    };
  }

  /* TRANSFORMA UN BASE64 A BLOB*/
  base64toBlob(base64Data, contentType) {
    contentType = contentType || '';
    var sliceSize = 1024;
    var byteCharacters = atob(base64Data);
    var bytesLength = byteCharacters.length;
    var slicesCount = Math.ceil(bytesLength / sliceSize);
    var byteArrays = new Array(slicesCount);

    for (var sliceIndex = 0; sliceIndex < slicesCount; ++sliceIndex) {
      var begin = sliceIndex * sliceSize;
      var end = Math.min(begin + sliceSize, bytesLength);

      var bytes = new Array(end - begin);
      for (var offset = begin, i = 0; offset < end; ++i, ++offset) {
        bytes[i] = byteCharacters[offset].charCodeAt(0);
      }
      byteArrays[sliceIndex] = new Uint8Array(bytes);
    }
    return new Blob(byteArrays, { type: contentType });
  }

  /* AYUDA A BUSCAR DENTRO DE LA RAMIFICACION DE UN ARBOL DEL COMPONNETE TREE DE ANGULAR */
  searchTree(element: ItemNode, matchingTitle): ItemNode {
    if (element.item == matchingTitle) {
      return element;
    } else if (element.children != null) {
      let i;
      let result = null;
      for (i = 0; result == null && i < element.children.length; i++) {
        result = this.searchTree(element.children[i], matchingTitle);
      }
      return result;
    }
    return null;
  }


  /*
  AYUDA A CONVERTIR UNA CADENA DE TEXTO A UN ARBOL EN FORMATO DE STRING
   */
  setNestedData(root, path, sep, value) {
    let paths = path.split(sep);
    let last_index = paths.length - 1;
    paths.forEach(function (key, index) {
      if (!(key in root)) root[key] = {};
      if (index == last_index) root[key] = value;
      root = root[key];
    });
    return root;
  }

  /*
  AYUDA A CONVERTIR UN OBJECTO DE STRING A UN OBJETO QUE PUEDE LEER EL COMPONENTE TREE DE ANGULAR
  */
  obtenerHijosPermiso(base): TreeviewItem[] {
    let returnChildren: TreeviewItem[] = [];
    let index: number;
    for (let key in base) {
      const item = new TreeviewItem({ text: 'Others', value: null, checked: false, disabled: false });
      item.text = key;
      if (typeof base[key] == "object") {
        let permisosDTO: PermisoPublicoDTO = <PermisoPublicoDTO>base[key];
        if (permisosDTO.id != null) {
          item.value = base[key];
          item.text += " | " + permisosDTO.descripcion;
          returnChildren.push(item);
        } else {
          let children = this.obtenerHijosPermiso(base[key]);
          item.children = children;
          returnChildren.push(item);
        }
      } else {
        item.value = base[key];
        returnChildren.push(item);
      }
    }
    return returnChildren;
  }




  convertDateToMX(fecha: string) {
    if (fecha != null || fecha != undefined) {
      const format = 'yyyy-MM-ddTHH:mm:ss.SS';
      const locale = 'es-MX';
      return formatDate(fecha, format, locale);
    }
  }

}
