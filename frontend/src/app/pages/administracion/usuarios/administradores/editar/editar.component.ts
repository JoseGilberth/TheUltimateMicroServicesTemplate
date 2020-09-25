import { HttpErrorResponse } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { TreeviewConfig, TreeviewItem } from 'ngx-treeview';
import { configuraciones } from '../../../../../../environments/configuraciones';
import { PermisoPublicoDTO } from '../../../../../_dto/usuarios/PermisoPublico.Dto';
import { UsuariosAdminDTO } from '../../../../../_dto/usuarios/UsuariosAdmin.Dto';
import { TimeUnitService } from '../../../../../_servicios/catalogos/timeunits.service';
import { PermisosAdminService } from '../../../../../_servicios/usuarios/administradores/permisosadmin.service';
import { UsuariosAdminService } from '../../../../../_servicios/usuarios/administradores/usuariosadmin.service';
import { UtilComponent } from '../../../../../_shared/util.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  isLoading: boolean = false;
  showPassword: boolean = false;

  public usuariosAdminDTO: UsuariosAdminDTO;
  public permisosAdminDTO: PermisoPublicoDTO[] = [];
  public permisosAdminSeleccionados: PermisoPublicoDTO[] = [];

  constructor(
    private permisosAdminService: PermisosAdminService
    , private utilComponent: UtilComponent
    , private router: Router
    , private usuariosAdminService: UsuariosAdminService
    , private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.usuariosAdminDTO = <UsuariosAdminDTO>JSON.parse(sessionStorage.getItem(configuraciones.static.usuario));
    this.listAllPermisos();

    this.formulario = this.formBuilder.group({
      id: [this.usuariosAdminDTO.id],
      username: [this.usuariosAdminDTO.username, Validators.compose([Validators.required, Validators.minLength(5)])],
      password: [''],
      repetirPassword: [''],
      correo: [this.usuariosAdminDTO.correo, Validators.compose([Validators.required, Validators.minLength(5), Validators.email])],
      nombre: [this.usuariosAdminDTO.nombre, Validators.compose([Validators.required, Validators.minLength(3)])],
      apellido1: [this.usuariosAdminDTO.apellido1, Validators.compose([Validators.required, Validators.minLength(3)])],
      apellido2: [this.usuariosAdminDTO.apellido2, Validators.compose([Validators.required, Validators.minLength(3)])],
      permisos: [this.usuariosAdminDTO.permisos, Validators.compose([Validators.required])],
      fechaAlta: [this.usuariosAdminDTO.fechaAlta],
      ultimaActualizacion: [this.usuariosAdminDTO.ultimaActualizacion],
      enabled: [this.usuariosAdminDTO.enabled]
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
    let fingerprint = this.usuariosAdminDTO.fingerPrintAuthentication;
    this.usuariosAdminDTO = this.formulario.value;
    this.usuariosAdminDTO.fingerPrintAuthentication = fingerprint;

    this.usuariosAdminService.actualizar(this.usuariosAdminDTO.id, this.usuariosAdminDTO)
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
    this.permisosAdminSeleccionados = event;
    this.f.permisos.setValue(this.permisosAdminSeleccionados);
    if (this.permisosAdminSeleccionados.length > 0) {
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
    this.permisosAdminSeleccionados = [];
  }


  /*
  ================================================================
                              REGRESAR
  ================================================================
  */
  public regresar(): void {
    this.router.navigate(["/usuarios/administradores"]);
  }

  /*
  ================================================================
                     OBTENER PERMISOS
  ================================================================
  */
  listAllPermisos() {
    this.isLoading = true;
    this.permisosAdminService.obtenerTodos()
      .subscribe(resp => {
        this.isLoading = false;
        this.permisosAdminDTO = resp.cuerpo;
        let nestedObjects = {};
        this.permisosAdminDTO.forEach(permiso => {
          this.utilComponent.setNestedData(nestedObjects, permiso.etiqueta, ':', permiso);
          this.permisos = this.utilComponent.obtenerHijosPermiso(nestedObjects);
          this.remarcaPermisosYaAdquiridos(this.permisos, this.usuariosAdminDTO.permisos);
        });
      }, (error: HttpErrorResponse) => {
        this.isLoading = false;
      });
  }

  remarcaPermisosYaAdquiridos(treeviewItems: TreeviewItem[], permisos: PermisoPublicoDTO[]) {
    treeviewItems.forEach(treeviewItem => {
      let nodoPermisoPublicoDto: PermisoPublicoDTO = treeviewItem.value;
      this.usuariosAdminDTO.permisos.forEach(permisoUsuario => { // TODOS LOS PERMISOS DEL USUARIO
        if (nodoPermisoPublicoDto != null) {
          if (nodoPermisoPublicoDto.id === permisoUsuario.id) {
            treeviewItem.checked = true;
            this.permisosAdminSeleccionados.push(nodoPermisoPublicoDto);
          }
        }
      });
      // REPITE EL PROCESO
      if (treeviewItem.children != null) {
        this.remarcaPermisosYaAdquiridos(treeviewItem.children, permisos);
      }
    });
  }




}
