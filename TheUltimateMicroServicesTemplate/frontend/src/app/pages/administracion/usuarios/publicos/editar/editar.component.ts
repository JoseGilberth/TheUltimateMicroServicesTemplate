import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TreeviewConfig, TreeviewItem } from 'ngx-treeview';
import { PermisoPublicoDTO } from '../../../../../_dto/usuarios/PermisoPublico.Dto';
import { UsuariosPublicosDTO } from '../../../../../_dto/usuarios/UsuariosPublicos.Dto';
import { TimeUnitService } from '../../../../../_servicios/catalogos/timeunits.service';
import { PermisosPublicosService } from '../../../../../_servicios/usuarios/publicos/permisospublicos.service';
import { UsuariosPublicosService } from '../../../../../_servicios/usuarios/publicos/usuariospublicos.service';
import { UtilComponent } from '../../../../../_shared/util.component';
import { configuraciones } from '../../../../../../environments/configuraciones';


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

  public usuariosPublicosDTO: UsuariosPublicosDTO;
  public permisosPublicosDTO: PermisoPublicoDTO[] = [];
  public permisosPublicosSeleccionados: PermisoPublicoDTO[] = [];

  constructor(private timeUnitService: TimeUnitService
    , private permisosPublicosService: PermisosPublicosService
    , private utilComponent: UtilComponent
    , private router: Router
    , private usuariosPublicosService: UsuariosPublicosService) {

  }

  ngOnInit(): void {
    this.usuariosPublicosDTO = <UsuariosPublicosDTO>JSON.parse(sessionStorage.getItem(configuraciones.static.usuario));
    this.usuariosPublicosDTO.password = "";
    this.usuariosPublicosDTO.repetirPassword = "";
    this.listAllTimeUnits();
    this.listAllPermisos();
  }


  actualizar() {
    this.utilComponent.showSweetAlertLoading("Actualizando", "");
    this.usuariosPublicosDTO.permisos = this.permisosPublicosSeleccionados;
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

  reinciarValores() {
    this.utilComponent.cleanProperties(this.usuariosPublicosDTO);
    this.utilComponent.cleanProperties(this.permisosPublicosSeleccionados);
  }

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
        this.permisosPublicosDTO = <PermisoPublicoDTO[]>resp.cuerpo;
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




}
