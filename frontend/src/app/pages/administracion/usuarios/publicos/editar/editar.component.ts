import { HttpErrorResponse } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { OrderDownlineTreeviewEventParser, TreeviewConfig, TreeviewEventParser, TreeviewItem } from 'ngx-treeview';
import { configuraciones } from '../../../../../../environments/configuraciones';
import { PermisoPublicoDTO } from '../../../../../_dto/usuarios/PermisoPublico.Dto';
import { UsuariosPublicosDTO } from '../../../../../_dto/usuarios/UsuariosPublicos.Dto';
import { TimeUnitService } from '../../../../../_servicios/catalogos/timeunits.service';
import { PermisosPublicosService } from '../../../../../_servicios/usuarios/publicos/permisospublicos.service';
import { UsuariosPublicosService } from '../../../../../_servicios/usuarios/publicos/usuariospublicos.service';
import { UtilComponent } from '../../../../../_shared/util.component';
import { ValidacionesComponent } from '../../../../../_shared/validaciones/validaciones';


@Injectable()
export class PermisosTreeViewConfig extends TreeviewConfig {
  hasAllCheckBox = true;
  hasFilter = true;
  hasCollapseExpand = true;
  decoupleChildFromParent = false;
  maxHeight = 500;
}

@Component({
  templateUrl: 'editar.component.html',
  providers: [
    { provide: TreeviewConfig, useClass: PermisosTreeViewConfig }
  ]
})
export class EditarUsuarioComponent {

  public formulario: FormGroup;
  permisos: TreeviewItem[];

  timeUnitsDto: string[] = [];
  isLoading: boolean = false;
  showPassword: boolean = false;

  public usuariosPublicosDTO: UsuariosPublicosDTO;
  public permisosPublicosDTO: PermisoPublicoDTO[] = [];
  public permisosPublicosSeleccionados: PermisoPublicoDTO[] = [];

  constructor(private timeUnitService: TimeUnitService
    , private permisosPublicosService: PermisosPublicosService
    , private utilComponent: UtilComponent
    , private router: Router
    , private usuariosPublicosService: UsuariosPublicosService
    , private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.usuariosPublicosDTO = <UsuariosPublicosDTO>JSON.parse(sessionStorage.getItem(configuraciones.static.usuario));

    this.listAllTimeUnits();
    this.listAllPermisos();
    this.formulario = this.formBuilder.group({
      id: [this.usuariosPublicosDTO.id],
      username: [this.usuariosPublicosDTO.username, Validators.compose([Validators.required, Validators.minLength(5)])],
      password: [''],
      repetirPassword: [''],
      correo: [this.usuariosPublicosDTO.correo, Validators.compose([Validators.required, Validators.minLength(5), Validators.email])],
      nombre: [this.usuariosPublicosDTO.nombre, Validators.compose([Validators.required, Validators.minLength(3)])],
      apellido1: [this.usuariosPublicosDTO.apellido1, Validators.compose([Validators.required, Validators.minLength(3)])],
      apellido2: [this.usuariosPublicosDTO.apellido2, Validators.compose([Validators.required, Validators.minLength(3)])],
      permisos: [this.usuariosPublicosDTO.permisos, Validators.compose([Validators.required])],
      limitRequest: [this.usuariosPublicosDTO.limitRequest, Validators.compose([Validators.required, ValidacionesComponent.minValue(1)])],
      periodRequest: [this.usuariosPublicosDTO.periodRequest, Validators.compose([Validators.required, ValidacionesComponent.minValue(1)])],
      timeUnitRequest: [this.usuariosPublicosDTO.timeUnitRequest, Validators.compose([Validators.required])],
      timeUnitToken: [this.usuariosPublicosDTO.timeUnitToken, Validators.compose([Validators.required])],
      tokenExpiration: [this.usuariosPublicosDTO.tokenExpiration, Validators.compose([Validators.required, ValidacionesComponent.minValue(1)])],
      fechaAlta: [this.usuariosPublicosDTO.fechaAlta],
      ultimaActualizacion: [this.usuariosPublicosDTO.ultimaActualizacion],
      enabled: [this.usuariosPublicosDTO.enabled]
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.formulario.controls; }


  /*
  ================================================================
                              ACTUALIZAR
  ================================================================
  */
  onSubmit() {

    if (this.formulario.invalid) {
      this.utilComponent.showSweetAlert("Error", "Aun hay algunos datos que debes revisar antes de actualizar un nuevo usuario, revisa los campos", "warning");
      return;
    }
    this.utilComponent.showSweetAlertLoading("Usuarios", "Estamos actualizando tu usuario");
    let fingerprint = this.usuariosPublicosDTO.fingerPrintAuthentication;
    this.usuariosPublicosDTO = this.formulario.value;
    this.usuariosPublicosDTO.fingerPrintAuthentication = fingerprint;

    this.usuariosPublicosService.actualizar(this.usuariosPublicosDTO.id, this.usuariosPublicosDTO)
      .subscribe(resp => {
        this.isLoading = false;
        this.utilComponent.showSweetAlert("Actualizado", resp.mensaje, "success");
        this.regresar();
      }, error => {
        this.isLoading = false;
        this.utilComponent.showSweetAlert("Error", this.utilComponent.trataErrores(error), "error");
      });
  }

  /*
  ================================================================
                    SELECCION DE PERMISOS
  ================================================================
  */
  onSelectedChange(event: any) {
    this.permisosPublicosSeleccionados = event;
    this.f.permisos.setValue(this.permisosPublicosSeleccionados);
    if (this.permisosPublicosSeleccionados.length > 0) {
      this.f.permisos.setErrors(null);
    } else {
      this.f.permisos.setErrors({ required: true });
    }
  }


  /*
  ================================================================
                      REINICIAR VALORES
  ================================================================
  */
  reiniciarValores() {
    this.formulario.reset();
    this.permisosPublicosSeleccionados = [];
  }

  /*
  ================================================================
                              REGRESAR
  ================================================================
  */
  public regresar(): void {
    this.router.navigate(["/usuarios/publicos"]);
  }

  /*
  ================================================================
                     OBTENER TIME UNITS
  ================================================================
  */
  listAllTimeUnits() {
    this.isLoading = true;
    this.timeUnitsDto = [];
    this.timeUnitService.obtenerTodos()
      .subscribe(resp => {
        this.isLoading = false;
        this.timeUnitsDto = resp.cuerpo;
      }, (error: HttpErrorResponse) => {
        this.isLoading = false;
      });
  }

  /*
  ================================================================
                     OBTENER PERMISOS
  ================================================================
  */
  listAllPermisos() {
    this.isLoading = true;
    this.timeUnitsDto = [];
    this.permisosPublicosService.obtenerTodos()
      .subscribe(resp => {
        this.isLoading = false;
        this.permisosPublicosDTO = resp.cuerpo;
        let nestedObjects = {};
        this.permisosPublicosDTO.forEach(permiso => {
          this.utilComponent.setNestedData(nestedObjects, permiso.etiqueta, ':', permiso);
          this.permisos = this.utilComponent.obtenerHijosPermiso(nestedObjects);
          this.remarcaPermisosYaAdquiridos(this.permisos, this.usuariosPublicosDTO.permisos);
        });
      }, (error: HttpErrorResponse) => {
        this.isLoading = false;
      });
  }


  remarcaPermisosYaAdquiridos(treeviewItems: TreeviewItem[], permisos: PermisoPublicoDTO[]) {
    treeviewItems.forEach(treeviewItem => {
      let nodoPermisoPublicoDto: PermisoPublicoDTO = treeviewItem.value;
      this.usuariosPublicosDTO.permisos.forEach(permisoUsuario => { // TODOS LOS PERMISOS DEL USUARIO
        if (nodoPermisoPublicoDto != null) {
          if (nodoPermisoPublicoDto.id === permisoUsuario.id) {
            console.log(nodoPermisoPublicoDto);
            treeviewItem.checked = true;
            this.permisosPublicosSeleccionados.push(nodoPermisoPublicoDto);
          }
        }
      });
      // REPITE EL PROCESO
      if (treeviewItem.children != null) {
        this.remarcaPermisosYaAdquiridos(treeviewItem.children, permisos);
      }
    });
  }

  byValue(item1: any, item2: any) {
    return item1 == item2;
  }

}
