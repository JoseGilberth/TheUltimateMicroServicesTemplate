// IMPORTANT: this is a plugin which requires jQuery for initialisation and data manipulation
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { PerfilesFiltroDTO } from 'src/app/_dto/principal/PerfilesFiltroDTO';
import { RolesDTO } from 'src/app/_dto/principal/UsuariosDTO';
import { PerfilesService } from 'src/app/_servicios/principal/perfiles.service';
import { UtilComponent } from 'src/app/_shared/util.component';
import swal from 'sweetalert2';
import { configuraciones } from 'src/environments/configuraciones';
import { CrearComponent } from './crear/crear.component';
import { EditarComponent } from './editar/editar.component';
import { FiltroComponent } from './filtro/filtro.component';
import { Router } from '@angular/router';


declare const $: any;

@Component({
  selector: 'app-data-table-cmp',
  templateUrl: 'perfiles.component.html'
})
export class PerfilesComponent implements OnInit, AfterViewInit {

  public configuraciones: any;
  public rolesDTO: RolesDTO[];
  public perfilesFiltroDTO: PerfilesFiltroDTO;
  private table: any;

  constructor(
    private perfilesService: PerfilesService
    , private translate: TranslateService
    , private util: UtilComponent
    , private router: Router
    , private dialog: MatDialog) {
    this.configuraciones = configuraciones;
  }

  ngOnInit() {
    this.perfilesFiltroDTO = new PerfilesFiltroDTO();
    this.perfilesFiltroDTO.activo = "true";
  }

  ngAfterViewInit() {
    this.initTable();
    $('.card .material-datatables label').addClass('form-group');
  }


  /*
  ================================================================
                          CARGAR ROLES
  ================================================================
  */
  initTable() {
    this.translate.get('data_tables.idioma').subscribe((idioma: string) => {
      this.table = $('#perfiles').DataTable({
        fixedHeader: true,
        scrollY: configuraciones.datatable.scrollY,
        responsive: true,
        serverSide: true,
        processing: true,
        deferRender: true,
        searching: false,
        pagingType: configuraciones.datatable.pagingType,
        pageLength: configuraciones.datatable.pageLength,
        lengthMenu: configuraciones.datatable.lengthMenu,
        language: idioma,
        ajax: (dataTablesParameters: any, callback) => {
          this.util.loading = true;
          let pagina = (parseInt(dataTablesParameters["start"]) / parseInt(dataTablesParameters["length"])) + 1;
          this.perfilesService.obtenerTodos(this.perfilesFiltroDTO, pagina, parseInt(dataTablesParameters["length"])).subscribe(resp => {
            this.util.loading = false;
            this.rolesDTO = <RolesDTO[]>resp.cuerpo;
            callback({
              recordsTotal: resp.total,
              recordsFiltered: resp.total,
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

        },// TERMINA AJAX
        columns: [
          { data: 'id', sortable: false }
          , { data: 'etiqueta', sortable: false }
          , { data: 'etiqueta', sortable: false }
          , { data: 'descripcion', sortable: false }
          , { data: 'activo', sortable: false }
          , { data: 'id', sortable: false }
        ],
        columnDefs: [

        ]
      });

    });
  }



  /*
  ================================================================
                          EDITAR ROLES
  ================================================================
  */
  editar(rol: RolesDTO) {
    sessionStorage.setItem(configuraciones.static.roles, JSON.stringify(rol));
    this.router.navigate(["/perfiles/editar"]);
  }
  /*
  ================================================================
                          CREAR ROLES
  ================================================================
  */
  nuevo() {
    this.router.navigate(["/perfiles/crear"]);
  }

  /*
  ================================================================
                          FILTRAR ROLES
  ================================================================
  */
  filtrar() {
    this.dialog.open(FiltroComponent, {
      width: '50%',
      height: "auto",
      autoFocus: true,
      disableClose: false,
      data: [this.perfilesFiltroDTO, this.table]
    });
  }

  quitarFiltro() {
    this.util.cleanProperties(this.perfilesFiltroDTO);
    this.table.ajax.reload();
  }

  /*
  ================================================================
                          ELIMINAR ROLES
  ================================================================
  */
  eliminar(rol: RolesDTO) {
    let contexto = this;
    /*SERVICIO DE TRADUCCIOn */
    this.translate.get(['principal.perfiles.eliminar', 'principal.perfiles.eliminar.texto'], { perfil: rol.etiqueta }).subscribe((eliminar: string) => {
      let traduccion = JSON.parse(JSON.stringify(eliminar['principal.perfiles.eliminar']));
      let texto = eliminar['principal.perfiles.eliminar.texto'];
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
            contexto.perfilesService.borrar(rol.id)
              .subscribe(
                resp => {
                  contexto.table.ajax.reload();
                  swal('Eliminado', resp.mensaje, 'success');
                },
                error => {
                  console.log("Error: " + JSON.stringify(error));
                  swal('Error', error.error.mensaje, 'error');
                }
              );
          });
        }
      }).then(function (data) {
        if (data.dismiss) {
          swal(traduccion.cancelado, traduccion.cancancelado_texto, 'error')
        }
      });
    });
  }


  /*
  ================================================================
                          ACTIVAR ROLES
  ================================================================
  */
  activar(rol: RolesDTO) {
    let contexto = this;
    /*SERVICIO DE TRADUCCIOn */
    this.translate.get(['principal.perfiles.activar', 'principal.perfiles.activar.texto'], { perfil: rol.etiqueta }).subscribe((activar: string) => {
      let traduccion = JSON.parse(JSON.stringify(activar['principal.perfiles.activar']));
      let texto = activar['principal.perfiles.activar.texto'];
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
            contexto.perfilesService.activar(rol.id)
              .subscribe(
                resp => {
                  contexto.table.ajax.reload();
                  swal('Activado', resp.mensaje, 'success');
                },
                error => {
                  swal('Error', traduccion.fallo, 'error');
                }
              );
          });
        }
      }).then(function (data) {
        if (data.dismiss) {
          swal(traduccion.cancelado, traduccion.cancancelado_texto, 'error')
        }
      });
    });
  }




}
