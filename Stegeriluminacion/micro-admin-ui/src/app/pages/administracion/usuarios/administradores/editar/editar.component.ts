import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TreeviewConfig, TreeviewItem } from 'ngx-treeview';
import { configuraciones } from '../../../../../../environments/configuraciones';
import { PermisoPublicoDTO } from '../../../../../_dto/usuarios/PermisoPublico.Dto';
import { UsuariosAdminDTO } from '../../../../../_dto/usuarios/UsuariosAdmin.Dto';
import { TimeUnitService } from '../../../../../_servicios/catalogos/timeunits.service';
import { UtilComponent } from '../../../../../_shared/util.component';
import { PermisosAdminService } from '../../../../../_servicios/usuarios/administradores/permisosadmin.service';
import { UsuariosAdminService } from '../../../../../_servicios/usuarios/administradores/usuariosadmin.service';


@Component({
  templateUrl: 'editar.component.html'
})
export class EditarUsuarioComponent {

  permisos: TreeviewItem[];
  config = TreeviewConfig.create({
    hasAllCheckBox: true,
    hasFilter: true,
    hasCollapseExpand: true,
    decoupleChildFromParent: false,
    maxHeight: 500
  });

  timeUnitsDto: string[] = [];
  isLoading: boolean = false;

  public usuariosAdminDTO: UsuariosAdminDTO;
  public permisosAdminDTO: PermisoPublicoDTO[] = [];
  public permisosAdminSeleccionados: PermisoPublicoDTO[] = [];

  constructor(private timeUnitService: TimeUnitService
    , private permisosAdminService: PermisosAdminService
    , private utilComponent: UtilComponent
    , private router: Router
    , private usuariosAdminService: UsuariosAdminService) {

  }

  ngOnInit(): void {
    this.usuariosAdminDTO = <UsuariosAdminDTO>JSON.parse(sessionStorage.getItem(configuraciones.static.usuario));
    this.usuariosAdminDTO.password = "";
    this.usuariosAdminDTO.repetirPassword = "";
    this.listAllTimeUnits();
    this.listAllPermisos();
  }


  actualizar() {
    this.utilComponent.showSweetAlertLoading("Actualizando", "");
    this.usuariosAdminDTO.permisos = this.permisosAdminSeleccionados;
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

  reinciarValores() {
    this.utilComponent.cleanProperties(this.usuariosAdminDTO);
    this.utilComponent.cleanProperties(this.permisosAdminSeleccionados);
  }

  public regresar(): void {
    this.router.navigate(["/usuarios/administradores"]);
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
    this.permisosAdminService.obtenerTodos()
      .subscribe(resp => {
        this.isLoading = false;
        this.permisosAdminDTO = <PermisoPublicoDTO[]>resp.cuerpo;
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
