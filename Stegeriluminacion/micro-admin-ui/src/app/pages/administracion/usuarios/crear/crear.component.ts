import { Component, ViewChild } from '@angular/core';
import { UsuariosPublicosDTO } from '../../../../_dto/usuarios/UsuariosPublicos.Dto';
import { TimeUnitService } from '../../../../_servicios/catalogos/timeunits.service';
import { HttpErrorResponse } from '@angular/common/http';
import { TreeviewItem, DropdownTreeviewComponent, TreeviewConfig, TreeviewI18n } from 'ngx-treeview';
import { PermisosPublicosService } from '../../../../_servicios/usuarios/permisospublicos.service';
import { PermisoPublicoDTO } from '../../../../_dto/usuarios/PermisoPublico.Dto';
import { UtilComponent } from '../../../../_shared/util.component';
import { UsuariosPublicosService } from '../../../../_servicios/usuarios/usuariospublicos.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';


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

  public usuariosPublicosDTO: UsuariosPublicosDTO;
  public permisosPublicosDTO: PermisoPublicoDTO[] = [];
  public permisosPublicosSeleccionados: PermisoPublicoDTO[];

  constructor(private timeUnitService: TimeUnitService
    , private permisosPublicosService: PermisosPublicosService
    , private utilComponent: UtilComponent
    , private router: Router
    , private usuariosPublicosService: UsuariosPublicosService) {

  }

  ngOnInit(): void {
    this.usuariosPublicosDTO = new UsuariosPublicosDTO();
    this.listAllTimeUnits();
    this.listAllPermisos();
  }


  onConfirm() {
    this.utilComponent.showSweetAlertLoading("Creando", "");
    this.usuariosPublicosDTO.permisos = this.permisosPublicosSeleccionados;
    this.usuariosPublicosService.crear(this.usuariosPublicosDTO)
      .subscribe(resp => {
        this.isLoading = false;
        this.utilComponent.showSweetAlert("Creado", resp.mensaje, "success");
      }, error => {
        this.isLoading = false;
        console.log("EL ERROR DENTRO DE CREAR: " + JSON.stringify(error));
        this.utilComponent.showSweetAlert("Error", this.utilComponent.trataErrores(error), "error");
      });
  }

  reinciarValores() {
    this.utilComponent.cleanProperties(this.usuariosPublicosDTO);
    this.utilComponent.cleanProperties(this.permisosPublicosSeleccionados);
  }

  public regresar(): void {
    this.router.navigate(["/usuarios"]);
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
        this.permisosPublicosDTO = <PermisoPublicoDTO[]>resp.cuerpo;
        let nestedObjects = {};
        this.permisosPublicosDTO.forEach(permiso => {
          this.utilComponent.setNestedData(nestedObjects, permiso.etiqueta, ':', permiso);
          this.permisos = this.utilComponent.obtenerHijosPermiso(nestedObjects);
        });
      }, (error: HttpErrorResponse) => {
        this.isLoading = false;
      });
  }


}
