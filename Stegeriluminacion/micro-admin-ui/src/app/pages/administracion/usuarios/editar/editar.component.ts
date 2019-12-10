import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { EmpresasDTO } from 'src/app/_dto/principal/EmpresasDTO';
import { EmpresasFiltroDTO } from 'src/app/_dto/principal/EmpresasFiltroDTO';
import { PerfilesFiltroDTO } from 'src/app/_dto/principal/PerfilesFiltroDTO';
import { RolesDTO, UsuariosDTO } from 'src/app/_dto/principal/UsuariosDTO';
import { EmpresasService } from 'src/app/_servicios/principal/empresas.service';
import { PerfilesService } from 'src/app/_servicios/principal/perfiles.service';
import { UsuariosService } from 'src/app/_servicios/principal/usuarios.service';
import { NotificationComponent } from 'src/app/_shared/notification.component';
import { UtilComponent } from 'src/app/_shared/util.component';
import { PasswordValidation } from 'src/app/_shared/validaciones/match';
import { ValidacionesComponent } from 'src/app/_shared/validaciones/validaciones';
import { configuraciones } from 'src/environments/configuraciones';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

declare const $: any;

@Component({
  selector: 'app-data-table-cmp',
  templateUrl: 'editar.component.html'
})
export class EditarComponent implements OnInit, AfterViewInit {

  /* CONFIGURACIONES */
  configuraciones: any;
  validacionesComponent: any;

  /* VARIABLES NECESARIAS LOS ROLES */
  public usuario: UsuariosDTO;
  public usuariosFG: FormGroup;

  //ROLES
  public perfilesFiltroDTO: PerfilesFiltroDTO;
  public rolesDTO: RolesDTO[];
  private tablaRoles: any;
  public rolesSeleccionados: RolesDTO[] = [];

  //EMPRESAS
  public empresasFiltroDTO: EmpresasFiltroDTO;
  public empresasDTO: EmpresasDTO[];
  private tablaEmpresas: any;
  public empresasSeleccionadas: EmpresasDTO[] = [];



  constructor(
    private translate: TranslateService
    , public util: UtilComponent
    , private perfilesService: PerfilesService
    , private usuariosService: UsuariosService
    , private empresasService: EmpresasService
    , private notificacion: NotificationComponent
    , private formBuilder: FormBuilder
    , private router: Router) {
    this.configuraciones = configuraciones;
    this.validacionesComponent = ValidacionesComponent;
  }

  ngOnInit() {
    this.usuario = <UsuariosDTO>JSON.parse(sessionStorage.getItem(configuraciones.static.usuario));
    this.usuariosFG = this.formBuilder.group({
      usuario: ['', Validators.compose([
        Validators.required
        , Validators.pattern(configuraciones.principal.usuarios.usuario.pattern)
        , Validators.minLength(configuraciones.principal.usuarios.usuario.min)
        , Validators.maxLength(configuraciones.principal.usuarios.usuario.max)])],

      password: ['', Validators.compose([
        , Validators.maxLength(configuraciones.principal.usuarios.password.max)])],

      passwordConfirm: ['', Validators.compose([
        , Validators.maxLength(configuraciones.principal.usuarios.passwordconfim.max)])],

      nombre: ['', Validators.compose([
        Validators.required
        , Validators.pattern(configuraciones.principal.usuarios.nombre.pattern)
        , Validators.minLength(configuraciones.principal.usuarios.nombre.min)
        , Validators.maxLength(configuraciones.principal.usuarios.nombre.max)])],

      primerApellido: ['', Validators.compose([
        Validators.required
        , Validators.pattern(configuraciones.principal.usuarios.primer_apellido.pattern)
        , Validators.minLength(configuraciones.principal.usuarios.primer_apellido.min)
        , Validators.maxLength(configuraciones.principal.usuarios.primer_apellido.max)])],

      segundoApellido: ['', Validators.compose([
        Validators.required
        , Validators.pattern(configuraciones.principal.usuarios.segundo_apellido.pattern)
        , Validators.minLength(configuraciones.principal.usuarios.segundo_apellido.min)
        , Validators.maxLength(configuraciones.principal.usuarios.segundo_apellido.max)])],

      correo: ['', Validators.compose([
        Validators.required
        , Validators.email
        , Validators.minLength(configuraciones.principal.usuarios.correo.min)
        , Validators.maxLength(configuraciones.principal.usuarios.correo.max)])]

    }, {
        validator: PasswordValidation.MatchPassword
      });


    //ROLES
    this.perfilesFiltroDTO = new PerfilesFiltroDTO();
    this.perfilesFiltroDTO.activo = "true";
    //Empresas
    this.empresasFiltroDTO = new EmpresasFiltroDTO();
  }


  ngAfterViewInit() {
    this.initTableEmpresas(this);
    this.initTableRoles(this);
    $('.card .material-datatables label').addClass('form-group');
  }



  actualizar() {

    this.translate.get('principal.usuarios.editar').subscribe((mensajes: string) => {// CARGA IDIOMA
      if (this.empresasSeleccionadas.length <= 0) {
        this.notificacion.showNotification(mensajes['validacion_empresas'], "top", "right", "warning")
        return;
      }
      if (this.rolesSeleccionados.length <= 0) {
        this.notificacion.showNotification(mensajes['validacion_perfiles'], "top", "right", "warning")
        return;
      }
      if (this.usuario.password != this.usuario.passwordConfirm) {
        this.notificacion.showNotification(mensajes['validacion_password'], "top", "right", "warning")
        return;
      }

      console.log("mensajes: " + JSON.stringify(mensajes));
      this.util.showLoading(mensajes['titulo'], mensajes['actualizando_usuario'], 'info');
      this.usuario.roles = this.rolesSeleccionados;
      this.usuario.empresas = this.empresasSeleccionadas;
      this.usuariosService.actualizar(this.usuario.id, this.usuario).subscribe(
        resp => {
          sessionStorage.setItem(configuraciones.static.usuario, JSON.stringify(this.usuario));
          swal(mensajes['actualizado'], resp.mensaje, 'success');
          this.regresar();
        },
        (error: HttpErrorResponse) => {
          console.log("HttpErrorResponse: " + JSON.stringify(error));
          if (error.error.isTrusted == null) {
            // TRADUCCION DEL ERROR
            this.translate.get('principal.usuarios.errores.' + error.error.codigo).subscribe((mensajeError: string) => {// CARGA IDIOMA
              swal(mensajes['error'], mensajeError, 'error');
            });
          } else {
            this.translate.get('erroresGenerales.error').subscribe((mensajeError: string) => {// CARGA IDIOMA
              swal(mensajes['error'], mensajeError, 'error');
            });
          }
        });

    });
  }


  /*
  ================================================================
                          CARGAR ROLES
  ================================================================
  */
  initTableRoles(context) {
    this.translate.get('data_tables.idioma').subscribe((idioma: string) => {
      this.tablaRoles = $('#roles').DataTable({
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
          this.perfilesService.obtenerTodos(this.perfilesFiltroDTO, pagina, dataTablesParameters["length"]).subscribe(resp => {
            this.util.loading = false;
            this.rolesSeleccionados = this.usuario.roles;
            this.rolesDTO = <RolesDTO[]>resp.cuerpo;
            this.rolesDTO.forEach(rol => {
              rol.total = null;
            });
            callback({
              recordsTotal: resp.total,
              recordsFiltered: resp.total,
              data: []
            });
          },
            error => {
            });

        },// TERMINA AJAX
        columns: [
          { data: 'id', sortable: false }
          , { data: 'etiqueta', sortable: false }
          , { data: 'descripcion', sortable: false }
          , { data: 'id', sortable: false }
        ]
      });

      // HACE TRIGGER A TODOS LOS CHECKBOX DENTRO DE LA TABLA
      $('thead input[name="selcciona_todos_roles"]', context.tablaRoles.table().container()).on('click', function (e) {
        if (this.checked) {
          $('#roles tbody input[type="checkbox"]:not(:checked)').trigger('click');
        } else {
          $('#roles tbody input[type="checkbox"]:checked').trigger('click');
        }
        e.stopPropagation();
      });
    });
  }


  /*
  ================================================================
                          CARGAR EMPRESAS
  ================================================================
  */
  initTableEmpresas(context) {
    this.translate.get('data_tables.idioma').subscribe((idioma: string) => {
      this.tablaEmpresas = $('#empresas').DataTable({
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
          this.empresasService.obtenerTodasFiltro(this.empresasFiltroDTO, pagina, parseInt(dataTablesParameters["length"])).subscribe(resp => {
            this.util.loading = false;
            this.empresasDTO = <EmpresasDTO[]>resp.cuerpo;
            this.empresasSeleccionadas = this.usuario.empresas;

            callback({
              recordsTotal: resp.total,
              recordsFiltered: resp.total,
              data: []
            });
          },
            error => {
            });

        },// TERMINA AJAX
        columns: [
          { data: 'id', sortable: false }
          , { data: 'razon_social', sortable: false }
          , { data: 'id', sortable: false }
        ]
      });

      // HACE TRIGGER A TODOS LOS CHECKBOX DENTRO DE LA TABLA
      $('thead input[name="selecciona_todas_empresas"]', context.tablaEmpresas.table().container()).on('click', function (e) {
        if (this.checked) {
          $('#empresas tbody input[type="checkbox"]:not(:checked)').trigger('click');
        } else {
          $('#empresas tbody input[type="checkbox"]:checked').trigger('click');
        }
        e.stopPropagation();
      });
    });
  }


  regresar(){
    this.router.navigate(["/usuarios"]);
  }


  cancelar() {
    let context = this;
    this.translate.get('principal.usuarios.cancelar_operacion').subscribe((regresar: any) => {
      swal({
        title: regresar.titulo,
        text: regresar.contenido,
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: regresar.aceptar,
        cancelButtonText: regresar.cancelar,
        showLoaderOnConfirm: true,
        preConfirm: function () {
          return new Promise(function (resolve, reject) {
            $(".swal2-cancel").hide();
            context.router.navigate(["/usuarios"]);
            swal.close();
          });
        },
        allowOutsideClick: false
      }).then(function (data) {
        if (data.dismiss) {
          swal(regresar.cancelado, regresar.cancelaste_operacion, 'error')
        }
      });
    });
  }



  /*
  ================================================================
                  ACTUALIZACION DEL ARREGLO DE LOS ROLES
  ================================================================
  */
  validaRolesSeleccionados(rol: RolesDTO, rolSeleccionado: any, todosLosRoles: any) {
    this.util.validaElementosEnTabla(this.rolesSeleccionados, rol, rolSeleccionado, todosLosRoles, this.rolesDTO.length);
  }

  /*
  ================================================================
                  ACTUALIZACION DEL ARREGLO DE LAS EMPRESAS
  ================================================================
  */
  validaEmpresasSeleccionadas(empresasDTO: EmpresasDTO, empresaSeleccionada: any, todasLasEmpresas: any) {
    this.util.validaElementosEnTabla(this.empresasSeleccionadas, empresasDTO, empresaSeleccionada, todasLasEmpresas, this.empresasDTO.length);
  }

}
