import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TreeviewConfig, TreeviewItem } from 'ngx-treeview';
import { PermisoPublicoDTO } from '../../../../../_dto/usuarios/PermisoPublico.Dto';
import { UsuariosAdminDTO } from '../../../../../_dto/usuarios/UsuariosAdmin.Dto';
import { TimeUnitService } from '../../../../../_servicios/catalogos/timeunits.service';
import { UtilComponent } from '../../../../../_shared/util.component';
import { PermisosAdminService } from '../../../../../_servicios/usuarios/administradores/permisosadmin.service';
import { UsuariosAdminService } from '../../../../../_servicios/usuarios/administradores/usuariosadmin.service';

@Component({
  templateUrl: 'crear.component.html'
})
export class CrearUsuarioComponent {

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
  public permisosAdminSeleccionados: PermisoPublicoDTO[];

  constructor(private timeUnitService: TimeUnitService
    , private permisosAdminService: PermisosAdminService
    , private utilComponent: UtilComponent
    , private router: Router
    , private usuariosAdminService: UsuariosAdminService) {

  }

  ngOnInit(): void {
    this.usuariosAdminDTO = new UsuariosAdminDTO();
    this.listAllTimeUnits();
    this.listAllPermisos();
  }


  onConfirm() {
    this.utilComponent.showSweetAlertLoading("Creando", "");
    this.usuariosAdminDTO.permisos = this.permisosAdminSeleccionados;
    this.usuariosAdminService.crear(this.usuariosAdminDTO)
      .subscribe(resp => {
        this.isLoading = false;
        this.utilComponent.showSweetAlert("Creado", resp.mensaje, "success");
        this.regresar();
      }, error => {
        this.isLoading = false;
        console.log("EL ERROR DENTRO DE CREAR: " + JSON.stringify(error));
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
        });
      }, (error: HttpErrorResponse) => {
        this.isLoading = false;
      });
  }


}
