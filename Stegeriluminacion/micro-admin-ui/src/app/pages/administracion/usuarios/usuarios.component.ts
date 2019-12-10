// IMPORTANT: this is a plugin which requires jQuery for initialisation and data manipulation
import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { UsuariosPublicosDTO } from 'src/app/_dto/usuarios/UsuariosPublicos.Dto';
import { UsuariosPublicosFiltroDTO } from 'src/app/_dto/usuarios/UsuariosPublicosFiltroDTO';
import { UsuariosPublicosService } from 'src/app/_servicios/usuariospublicos.service';
import { UtilComponent } from 'src/app/_shared/util.component';
import { configuraciones } from 'src/environments/configuraciones';
import swal from 'sweetalert2';
import { FiltroComponent } from './filtro/filtro.component';
import { PageableDTO } from 'src/app/_dto/_main/Pageable.Dto';


declare const $: any;
@Component({
  selector: 'app-data-table-cmp',
  templateUrl: 'usuarios.component.html'
})
export class UsuariosComponent implements OnInit, AfterViewInit {

  public configuraciones: any;
  public utilComponent: any;

  public usuariosPublicosDTO: UsuariosPublicosDTO[];
  public usuariosPublicosFiltroDTO: UsuariosPublicosFiltroDTO;
  private table: any;

  constructor(
    private usuariosPublicosService: UsuariosPublicosService
    , private translate: TranslateService
    , private util: UtilComponent
    , private router: Router
    , private dialog: MatDialog) {

    this.configuraciones = configuraciones;
    this.utilComponent = util;

  }

  ngOnInit() {
    this.usuariosPublicosFiltroDTO = new UsuariosPublicosFiltroDTO();
  }

  ngAfterViewInit() {
    this.initTable();
    $('.card .material-datatables label').addClass('form-group');
  }

  /*
  ================================================================
                          CARGAR USUARIOS
  ================================================================
  */
  initTable() {
    this.translate.get('data_tables.idioma').subscribe((idioma: string) => {
      this.table = $('#usuarios').DataTable({
        responsive: true,
        autoWidth: true,
        fixedColumns: true,
        fixedHeader: true,
        "deferRender": true,
        serverSide: true,
        processing: true,
        searching: false,
        pagingType: configuraciones.datatable.pagingType,
        pageLength: configuraciones.datatable.pageLength,
        lengthMenu: configuraciones.datatable.lengthMenu,
        language: idioma,
        ajax: (dataTablesParameters: any, callback) => {

          this.util.loading = true;
          let pagina = (parseInt(dataTablesParameters["start"]) / parseInt(dataTablesParameters["length"])) + 1;
          this.usuariosPublicosService.obtenerTodos(this.usuariosPublicosFiltroDTO, (pagina - 1), parseInt(dataTablesParameters["length"])).subscribe(resp => {
            this.util.loading = false;
            let pageable: PageableDTO = <PageableDTO>resp.cuerpo;
            this.usuariosPublicosDTO = pageable.content;
            callback({
              recordsTotal: resp.cuerpo.totalElements,
              recordsFiltered: resp.cuerpo.totalElements,
              data: []
            });
          },
            error => {
              this.util.loading = false;
              callback({
                recordsTotal: 0,
                recordsFiltered: 0,
                data: []
              });
            });
        },
        // TERMINA AJAX
        columns: [
          { data: 'id', sortable: false }
          , { data: 'id', sortable: false }
          , { data: 'username', sortable: false }
          , { data: 'correo', sortable: false }
          , { data: 'nombre', sortable: false }
          , { data: 'apellido1', sortable: false }
          , { data: 'apellido2', sortable: false }
          , { data: 'telefonoCelular', sortable: false }
          , { data: 'limitRequest', sortable: false }
          , { data: 'timeUnit', sortable: false }
          , { data: 'tokenExpiration', sortable: false }
          , { data: 'fechaAlta', sortable: false }
          , { data: 'enabled', sortable: false }
        ]
      });
    });
  }


  /*
  ================================================================
                          EDITAR USUARIO
  ================================================================
  */
  editar(usuario: UsuariosPublicosDTO) {
    sessionStorage.setItem(configuraciones.static.usuario, JSON.stringify(usuario));
    this.router.navigate(["/usuarios/editar"]);
  }

  /*
  ================================================================
                          CREAR USUARIO
  ================================================================
  */
  nuevo() {
    this.router.navigate(["/usuarios/crear"]);
  }


  /*
  ================================================================
                          FILTRAR USUARIOS
  ================================================================
  */
  filtrar() {
    this.dialog.open(FiltroComponent, {
      width: '50%',
      height: 'auto',
      autoFocus: true,
      disableClose: false,
      data: [this.usuariosPublicosFiltroDTO, this.table]
    });
  }
  quitarFiltro() {
    this.util.cleanProperties(this.usuariosPublicosFiltroDTO);
    this.usuariosPublicosFiltroDTO.enabled = true;
    this.table.ajax.reload();
  }

  /*
  ================================================================
                          ELIMINAR USUARIO
  ================================================================
  */
  eliminar(usuario: UsuariosPublicosDTO) {
    let contexto = this;
    /*SERVICIO DE TRADUCCIOn */
    this.translate.get(['principal.usuarios.eliminar', 'principal.usuarios.eliminar.texto'], { usuario: usuario.username }).subscribe((eliminar: string) => {
      let traduccion = JSON.parse(JSON.stringify(eliminar['principal.usuarios.eliminar']));
      let texto = eliminar['principal.usuarios.eliminar.texto'];
      /* INICIA EL MODAL DE ELIMINACION */
      swal({
        title: traduccion.titulo,
        text: texto,
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: traduccion.aceptar,
        cancelButtonText: traduccion.cancelar,
        showLoaderOnConfirm: true,
        preConfirm: function () {
          return new Promise(function (resolve, reject) {
            $(".swal2-cancel").hide();
            contexto.usuariosPublicosService.borrar(usuario.id)
              .subscribe(
                resp => {
                  contexto.table.ajax.reload();
                  swal(traduccion.eliminado, resp.mensaje, 'success');
                },
                (error: HttpErrorResponse) => {
                  console.log("ERROR: " + JSON.stringify(error));
                  if (error.error.isTrusted == null) {
                    this.translate.get('principal.usuarios.errores.' + error.error.codigo).subscribe((mensajeError: string) => {// CARGA IDIOMA
                      swal(traduccion.error, mensajeError, 'error');
                    });
                  } else {
                    this.translate.get('erroresGenerales.error').subscribe((mensajeError: string) => {// CARGA IDIOMA
                      swal(traduccion.error, mensajeError, 'error');
                    });
                  }
                }
              );
          });
        },
        allowOutsideClick: false
      }).then(function (data) {
        if (data.dismiss) {
          swal(traduccion.cancelado, traduccion.cancelaste_operacion, 'error')
        }
      });
    });
  }


  /*
  ================================================================
                          ACTIVAR USUARIO
  ================================================================
  */
  activar(usuario: UsuariosPublicosDTO) {
    let contexto = this;
    /*SERVICIO DE TRADUCCIOn */
    this.translate.get(['principal.usuarios.activar', 'principal.usuarios.activar.texto'], { usuario: usuario.username }).subscribe((activar: string) => {
      let traduccion = JSON.parse(JSON.stringify(activar['principal.usuarios.activar']));
      let texto = activar['principal.usuarios.activar.texto'];
      /* INICIA EL MODAL DE ELIMINACION */
      swal({
        title: traduccion.titulo,
        text: texto,
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: traduccion.aceptar,
        cancelButtonText: traduccion.cancelar,
        showLoaderOnConfirm: true,
        allowOutsideClick: false,
        preConfirm: function () {
          return new Promise(function (resolve, reject) {
            $(".swal2-cancel").hide();
            contexto.usuariosPublicosService.activar(usuario.id)
              .subscribe(
                resp => {
                  contexto.table.ajax.reload();
                  swal(traduccion.activado, resp.mensaje, 'success');
                },
                (error: HttpErrorResponse) => {
                  console.log("ERROR: " + JSON.stringify(error));
                  if (error.error.isTrusted == null) {
                    this.translate.get('principal.usuarios.activar.errores.' + error.error.codigo).subscribe((mensajeError: string) => {// CARGA IDIOMA
                      swal(traduccion.error, mensajeError, 'error');
                    });
                  } else {
                    this.translate.get('erroresGenerales.error').subscribe((mensajeError: string) => {// CARGA IDIOMA
                      swal(traduccion.error, mensajeError, 'error');
                    });
                  }
                }
              );
          });
        }
      }).then(function (data) {
        if (data.dismiss) {
          swal(traduccion.cancelado, traduccion.cancelaste_operacion, 'error')
        }
      });
    });
  }




}
