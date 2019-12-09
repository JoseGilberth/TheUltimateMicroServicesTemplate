import { OnInit } from '@angular/core';
import { configuraciones } from 'src/environments/configuraciones';
import swal, { SweetAlertType } from 'sweetalert2'; 
import { ItemNode } from './arbol/ItemNode';

declare const $: any;

export class UtilComponent implements OnInit {

    loading: boolean = false;
    seleccionEmpresa: boolean = true;
    seleccionSistema: boolean = true;
    empresaSeleccionada: string = "Seleccione una empresa";
    sysERPSeleccionado: string = "Seleccione un sistema ERP";

    constructor() {
    }

    ngOnInit() {
    }

    /*
    ================================================================
                   VERIFICA SI HAY UNA EMPRESA SELECCIONADA
    ================================================================
    */
    showLoading(titulo: string, texto: string, tipo: SweetAlertType) {
        swal({
            title: texto, text: texto, type: tipo, showLoaderOnConfirm: true, allowOutsideClick: false,
            onBeforeOpen: () => {
                swal.showLoading()
            },
        });
    }

    /*
    ================================================================
                    ACTUALIZACION DE LOS CHECBOX
    ================================================================
    */
    validaElementosEnTabla(arreglo: any, dto: any, elementoCheckBoxSelected: any, elementosSelected: any, tamanoTotal: number) {
        var index = this.arrayObjectIncludes(arreglo, dto, "id");
        if (index === -1 && !elementoCheckBoxSelected.checked) { // -1 y False
            arreglo.push(dto);
            elementoCheckBoxSelected.checked = true;
        } else if (index !== -1 && elementoCheckBoxSelected.checked) { // 0 y true
            arreglo.splice(index, 1);
            elementoCheckBoxSelected.checked = false;
        } else if (index === -1 && elementoCheckBoxSelected.checked) { // -1 y true
            arreglo.push(dto);
            elementoCheckBoxSelected.checked = true;
        } else if (index !== -1 && !elementoCheckBoxSelected.checked) { // 0 y false
            arreglo.splice(index, 1);
            elementoCheckBoxSelected.checked = false;
        }
        if (arreglo.length >= tamanoTotal) {
            elementosSelected.checked = true;
        } else if (arreglo.length < tamanoTotal) {
            elementosSelected.checked = false;
        }
        return arreglo;
    }


    /* VALIDA SI UN VALOR SE ENCUENTRA DENTRO DE UN ARREGLO POR MEDIO DE UNA PROPIEDAD EN ESPECIFICO */
    arrayObjectIndexOf(myArray, searchTerm, property) {
        for (var i = 0, len = myArray.length; i < len; i++) {
            if (myArray[i][property] === searchTerm) return true;
        }
        return false;
    }


    creaParametros(dto: any): string {
        let contador: number = 0;
        let parametros: string = "";
        Object.keys(dto).forEach((key) => {
            if (dto[key] != null) {
                if (dto[key] != false) {
                    if (contador > 0) {
                        parametros += "?" + key + "=" + dto[key];
                    } else {
                        parametros += "&" + key + "=" + dto[key];
                    }
                }
            }
            contador++;
        });
        return parametros;
    }



    /* 
        FUNCION QUE MATCHEA UNA PROPIEDAD DE DTO Y OBTIENE SU VALOR , PARA DESPUES SER ENCONTRADA COMO PROPIEDAD
        DENTRO DE UN ARREGLO , PARA ENCONTRAR UNA COINCIDENCIA; SI COINCIDE REGRESA EL INDICE DEL OBJETO EN EL QUE COINCIDIO
        SI NO COINCIDE , ENTONCES REGRESA -1
    */
    arrayObjectIncludes(myArray, dto, property) {
        let objeto: any = myArray;
        let valor: any;
        let coincide: number = -1;
        Object.keys(dto).forEach((key) => {
            if (key.includes(property)) {
                if (dto[key] != null) {
                    if (dto[key] != false) {
                        valor = dto[key];
                        console.log("valor; " + valor);
                        return;
                    }
                }
            }
        });
        for (var i = 0, len = myArray.length; i < len; i++) {
            Object.keys(objeto[i]).forEach((key) => {
                if (key.includes(property)) {
                    if (objeto[i][key] == valor) {
                        coincide = i;
                        return;
                    }
                }
            });
        }
        return coincide;
    }


    /* VALIDA SI UN OBJETO TIENE PROPIEDADES VACIAS O NULAS */
    checkProperties(obj) {
        for (var key in obj) {
            if (obj[key] !== null && obj[key] != "")
                return false;
        }
        return true;
    }

    /* LIMPIA LAS PROPIEDADES DE UN OBJETO */
    cleanProperties(objeto) {
        Object.keys(objeto).forEach((key, indice) => {
            if (objeto[key] != null) {// SI EL VALOR DEL OBJETO NO ES NULLO
                if ((objeto[key]).toString() === "[object Object]") { // SI EL OBJETO ES UN OBJETO
                    this.cleanProperties(objeto[key]);//RECORRO PARA LIMPIAR
                } else {
                    objeto[key] = ""; //LIMPIO
                }
            } else { // SI ES NULLO SE VA A VACIo
                objeto[key] = "";
            }
        });
        console.log("JSON: " + JSON.stringify(objeto));
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


    seleccionaEmpresa(datos: any) {
        this.seleccionEmpresa = false;
        // console.log("EMPRESA: " + this.seleccionEmpresa);
        swal.close();
        swal({
            title: "Selección de empresa",
            text: "Seleccione una empresa para continuar",
            input: 'select',
            inputOptions: datos,
            inputPlaceholder: 'Seleccione',
            type: 'warning',
            showCancelButton: false,
            confirmButtonText: "Aceptar",
            cancelButtonText: "Cancelar",
            showLoaderOnConfirm: true,
            inputValidator: (valor) => {
                return new Promise((resolve) => {
                    console.log("VALOR DE EMPRESA: " + JSON.stringify(valor));
                    if (valor.trim() != "") {
                        localStorage.setItem(configuraciones.static.empresas, valor);
                        this.empresaSeleccionada = JSON.parse(valor).razon_social;
                        this.seleccionEmpresa = true;
                        swal.close();
                        this.sysERPSeleccionado = "Seleccione un sistema ERP";
                        localStorage.setItem(configuraciones.static.sistemaERP, null);
                    } else {
                        resolve('Deberá seleccionar una empresa')
                    }
                })
            },
            allowOutsideClick: false
        }).then(function (data) {
            if (data.dismiss) {
                swal("Whoops", "Es necesario seleccionar una empresa", 'error')
            }
        });
    }

    hasPermiso(permiso: string) {
        try {
            let permisos: string[] = <string[]>JSON.parse(localStorage.getItem(configuraciones.static.permisos));
            let index = permisos.indexOf(permiso);
            if (index != -1) { // EXISTE 
                //console.log("Tienes el permiso");
                return true;
            } else { // NO EXISTE
                //console.log("permisos insuficientes");
                return false;
            }
        } catch (exception) {
            //console.log("Error: permisos insuficientes");
            return false;
        }
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
     
    obtenerHijosPermiso(base): ItemNode[] {
        let returnChildren: ItemNode[] = [];
        let index: number;
        for (let key in base) {
            let item = new ItemNode();// NODO
            item.item = key;
            if (typeof base[key] == "object") {
                let permisosDTO: PermisosDTO = <PermisosDTO>base[key];
                if (permisosDTO.id != null) {
                    item.value = base[key];
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
    */

    /* OBTENER FECHA CON FORMATO YYYY-MM-DDTHH:MM:SS Obtenido del datepicker*/
    fechaConvert(fecha) {
        console.log(fecha);
        let date = fecha.split(' ');
        let mes;
        switch (date[1]) {
            case 'Jan': mes = '1'; break;
            case 'Feb': mes = '2'; break;
            case 'Mar': mes = '3'; break;
            case 'Apr': mes = '4'; break;
            case 'May': mes = '5'; break;
            case 'Jun': mes = '6'; break;
            case 'Jul': mes = '7'; break;
            case 'Aug': mes = '8'; break;
            case 'Sep': mes = '9'; break;
            case 'Oct': mes = '10'; break;
            case 'Nov': mes = '11'; break;
            case 'Dec': mes = '12'; break;
        }
        console.log(date[3] + '-' + mes + '-' + date[2]);
        return date[3] + '-' + mes + '-' + date[2];
    }

    /* OBTENER FECHA CON FORMATO YYYY-MM-DDTHH:MM:SS Obtenido del datepicker*/
    fechaConvert2(fecha) {
        console.log(fecha);
        let date = fecha.split(' ');
        let mes;
        switch (date[1]) {
            case 'Jan': mes = '01'; break;
            case 'Feb': mes = '02'; break;
            case 'Mar': mes = '03'; break;
            case 'Apr': mes = '04'; break;
            case 'May': mes = '05'; break;
            case 'Jun': mes = '06'; break;
            case 'Jul': mes = '07'; break;
            case 'Aug': mes = '08'; break;
            case 'Sep': mes = '09'; break;
            case 'Oct': mes = '10'; break;
            case 'Nov': mes = '11'; break;
            case 'Dec': mes = '12'; break;
        }
        return date[3] + '-' + mes + '-' + date[2];
    }

    /* OBTENER FECHA dd/MM/yyyy CON DEL FORMATO YYYY-MM-DDTHH:MM:SS*/
    obtenerFecha(resp) {
        for (var i = 0; i < resp.cuerpo.length; i++) {
            let fecha = resp.cuerpo[i].fecha.split('T');
            console.log(fecha[0]);
            let fechaD = fecha[0].split('-');

            resp.cuerpo[i].fecha = fechaD[2] + '/' + fechaD[1] + '/' + fechaD[0];
        }
        return resp.cuerpo;
    }
    obtenerFechaERP(resp) {
        console.log(resp);
        for (var i = 0; i < resp.length; i++) {
            let fecha = resp[i].fechaOrden.split('T');
            console.log(fecha[0]);
            let fechaD = fecha[0].split('-');

            resp[i].fechaOrden = fechaD[2] + '/' + fechaD[1] + '/' + fechaD[0];
        }
        return resp;
    }

    // OBTENER FECHA GENERICO
    obtenerFechaGen(resp) {
        let fecha = resp.split('T');
        let fechaD = fecha[0].split('-');
        return fechaD[2] + '/' + fechaD[1] + '/' + fechaD[0];
    }

    obtenerFechaDclrcn(resp) {
        for (var i = 0; i < resp.cuerpo.length; i++) {
            let fecha = resp.cuerpo[i].fecha_presentada.split('T');
            let fechaD = fecha[0].split('-');

            resp.cuerpo[i].fecha_presentada = fechaD[2] + '/' + fechaD[1] + '/' + fechaD[0];
        }
        return resp.cuerpo;
    }

    fechaLog(resp) {
        for (var i = 0; i < resp.cuerpo.length; i++) {
            let fecha = resp.cuerpo[i].fecha.split('T');
            let fechaD = fecha[0].split('-');
            resp.cuerpo[i].fecha = fechaD[2] + '/' + fechaD[1] + '/' + fechaD[0] + ' | ' + fecha[1];
        }
        return resp.cuerpo;
    }

    // CONVIERTE DE "YYYY-MM-DDT00:00:00" string A [yyyy,mm,dd] PARA DATEPICKER DE MATERIAL ANGULAR
    minMax(fecha) {
        let t = fecha.split('T');
        let date = t[0].split('-');
        date[0] = parseInt(date[0], 10);
        date[1] = parseInt(date[1], 10) - 1;
        date[2] = parseInt(date[2], 10);
        return date;
    }

    deducibles(deducible) {
        deducible = deducible ? 'Deducible' : 'No Deducible';
        return deducible;
    }


    tipoFecha(filtroFacturas) {

        switch (filtroFacturas.radio) {
            case 'true':
                if (filtroFacturas.fecha_dia) {
                    filtroFacturas.fecha_inicio = this.fechaConvert(filtroFacturas.fecha_dia.toString()) + 'T00:00:00';
                    filtroFacturas.fecha_fin = this.fechaConvert(filtroFacturas.fecha_dia.toString()) + 'T23:59:59';
                }
                break;
            case 'false':

                if (filtroFacturas.fecha_inicio) {
                    filtroFacturas.fecha_inicio = this.fechaConvert(filtroFacturas.fecha_inicio.toString()) + 'T00:00:00';

                }

                if (filtroFacturas.fecha_fin) {
                    filtroFacturas.fecha_fin = this.fechaConvert(filtroFacturas.fecha_fin.toString()) + 'T23:59:59';
                }
                break;
            default:
                break;
        }
        // filtroFacturas.fecha_inicio = filtroFacturas.fecha_inicio.replace(/:/g,'%3A');
        // filtroFacturas.fecha_fin = filtroFacturas.fecha_fin.replace(/:/g,'%3A');
        return filtroFacturas;
    }
    

}
