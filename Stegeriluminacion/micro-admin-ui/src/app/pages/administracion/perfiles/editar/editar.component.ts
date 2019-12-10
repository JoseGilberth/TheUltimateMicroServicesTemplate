import { SelectionModel } from '@angular/cdk/collections';
import { FlatTreeControl } from '@angular/cdk/tree';
import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatTreeFlatDataSource, MatTreeFlattener } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { PermisosDTO } from 'src/app/_dto/principal/PermisosDTO';
import { RolesDTO } from 'src/app/_dto/principal/UsuariosDTO';
import { PerfilesService } from 'src/app/_servicios/principal/perfiles.service';
import { PermisosService } from 'src/app/_servicios/principal/permisos.service';
import { ItemFlatNode } from 'src/app/_shared/arbol/ItemFlatNode';
import { ItemNode } from 'src/app/_shared/arbol/ItemNode';
import { NotificationComponent } from 'src/app/_shared/notification.component';
import { UtilComponent } from 'src/app/_shared/util.component';
import swal from 'sweetalert2';

import { configuraciones } from 'src/environments/configuraciones';
import { ValidacionesComponent } from 'src/app/_shared/validaciones/validaciones';
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

  /* VARIABLES NECESARIAS PARA EL ARBOL DE PERMISOS */
  flatNodeMap = new Map<ItemFlatNode, ItemNode>();
  nestedNodeMap = new Map<ItemNode, ItemFlatNode>();
  treeControl: FlatTreeControl<ItemFlatNode>;
  treeFlattener: MatTreeFlattener<ItemNode, ItemFlatNode>;
  dataSource: MatTreeFlatDataSource<ItemNode, ItemFlatNode>;
  checklistSelection = new SelectionModel<ItemFlatNode>(true /* multiple */);

  /* VARIABLES NECESARIAS LOS ROLES */
  public rolesDTO: RolesDTO;
  public rolesFG: FormGroup;

  /* VARIABLES NECESARIAS LOS PERMISOS */
  public permisosDTO: PermisosDTO[] = [];
  permisosSeleccionados: PermisosDTO[] = [];
  permisos: ItemNode[] = [];

  constructor(
    private translate: TranslateService
    , private router: Router
    , public util: UtilComponent
    , private permisosService: PermisosService
    , private perfilesService: PerfilesService
    , private notificacion: NotificationComponent
    , private formBuilder: FormBuilder) {

    /* ARBOL*/
    this.treeFlattener = new MatTreeFlattener(this.transformer, this.getLevel, this.isExpandable, this.getChildren);
    this.treeControl = new FlatTreeControl<ItemFlatNode>(this.getLevel, this.isExpandable);
    this.dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);
    this.configuraciones = configuraciones;
    this.validacionesComponent = ValidacionesComponent;
  }

  ngOnInit() {
    this.rolesDTO = <RolesDTO>JSON.parse(sessionStorage.getItem(configuraciones.static.roles));

    this.permisosSeleccionados = this.rolesDTO.permisos;
    this.rolesFG = this.formBuilder.group({
      nombre: ['', Validators.compose([
        Validators.required
        , Validators.pattern(configuraciones.principal.perfiles.nombre.pattern)
        , Validators.minLength(configuraciones.principal.perfiles.nombre.min)
        , Validators.maxLength(configuraciones.principal.perfiles.nombre.max)])],
      descripcion: ['', Validators.compose([
        Validators.required
        , Validators.pattern(configuraciones.principal.perfiles.descripcion.pattern)
        , Validators.minLength(configuraciones.principal.perfiles.descripcion.min)
        , Validators.maxLength(configuraciones.principal.perfiles.descripcion.max)])]
    });

    this.util.loading = true;
    this.dataSource.data = []; // IMPORTANTE INICIARLIZARLO EN VACIo
    this.permisosService.obtenerTodos("", 1, 1000).subscribe(
      resp => {
        this.permisosDTO = <PermisosDTO[]>resp.cuerpo;
        let nestedObjects = {};
        this.permisosDTO.forEach(permiso => {
          this.util.setNestedData(nestedObjects, permiso.etiqueta, ':', permiso);
          this.permisos = this.util.obtenerHijosPermiso(nestedObjects);
        });
        // CHECAMOS A TRUE SI ES QUE YA CUENTA CON EL PERMISO
        this.dataSource.data = this.permisos;
        for (let i = 0; i <= this.treeControl.dataNodes.length - 1; i++) {// NODOS DEL ARBOL
          for (let a = 0; a <= this.permisosSeleccionados.length - 1; a++) {
            // ITEM FLA NODE
            let itemFlatNode: ItemFlatNode = <ItemFlatNode>this.treeControl.dataNodes[i];
            if (itemFlatNode.value != undefined) {
              let permiso: PermisosDTO = <PermisosDTO>itemFlatNode.value;
              let permisoSeleccionado = this.permisosSeleccionados[a];
              if (permiso.id == permisoSeleccionado.id) {
                this.checklistSelection.toggle(this.treeControl.dataNodes[i]);
              }
            }
          }
        }
        this.util.loading = false;
      }, error => {
      });
  }


  ngAfterViewInit() {

  }

  actualizar() {
    this.translate.get('principal.perfiles.editar').subscribe((mensajes: string) => {// CARGA IDIOMA
      // OBTENGO LOS PERMISOS SELECCIONADOS
      this.permisosSeleccionados = [];
      for (let i = 0; i <= this.checklistSelection.selected.length - 1; i++) {
        let itemFlatNode: ItemFlatNode = <ItemFlatNode>this.checklistSelection.selected[i];
        if (itemFlatNode.value != undefined) {
          let permiso: PermisosDTO = <PermisosDTO>itemFlatNode.value;
          this.permisosSeleccionados.push(permiso);
        }
      }

      //VALIDA PERMISOS SELECCIOANDOS
      if (this.permisosSeleccionados.length <= 0) {
        this.notificacion.showNotification(mensajes['validacion_permisos'], "top", "right", "warning")
        return;
      }
      // INICIA LA CREACION DEL PERFIL AL WS
      this.util.showLoading(mensajes['titulo'], mensajes['actualizando_perfil'], 'info');
      this.rolesDTO.permisos = this.permisosSeleccionados;
      this.perfilesService.actualizar(this.rolesDTO.id, this.rolesDTO).subscribe(
        resp => {
          sessionStorage.setItem(configuraciones.static.roles, JSON.stringify(this.rolesDTO));
          swal(mensajes['actualizado'], resp.mensaje, 'success');
          this.regresar();
        },
        (error: HttpErrorResponse) => {
          console.log("ERROR: " + JSON.stringify(error));
          if (error.error.isTrusted == null) {
            // TRADUCCION DEL ERROR
            this.translate.get('principal.perfiles.errores.' + error.error.codigo).subscribe((mensajeError: string) => {// CARGA IDIOMA
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



  regresar(){
    this.router.navigate(["/perfiles"]);
  }
  
  cancelar() {
    let context = this;
    this.translate.get('principal.perfiles.cancelar_operacion').subscribe((regresar: any) => {
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
            context.router.navigate(["/perfiles"]);
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
  =======================================================
  FUNCIONES PARA EL COMPONENTE ARBOL DE NODOS  
  =======================================================
  */

  /* 
  FUNCIONES PARA EL COMPONENTE ARBOL DE NODOS  
  */
  getLevel = (node: ItemFlatNode) => node.level;
  isExpandable = (node: ItemFlatNode) => node.expandable;
  getChildren = (node: ItemNode): ItemNode[] => node.children;
  hasChild = (_: number, _nodeData: ItemFlatNode) => _nodeData.expandable;
  hasNoContent = (_: number, _nodeData: ItemFlatNode) => _nodeData.item === '';
  /**
   * Transformer to convert nested node to flat node. Record the nodes in maps for later use.
   */
  transformer = (node: ItemNode, level: number) => {
    let descripcion: string = "";
    if (node.value != undefined) {
      descripcion = " | Descripción: " + node.value.descripcion;
    }
    const existingNode = this.nestedNodeMap.get(node);
    const flatNode = existingNode && existingNode.item === node.item ? existingNode : new ItemFlatNode();
    flatNode.item = node.item + descripcion;
    flatNode.level = level;
    flatNode.value = node.value;
    flatNode.expandable = !!node.children;
    this.flatNodeMap.set(flatNode, node);
    this.nestedNodeMap.set(node, flatNode);
    return flatNode;
  }

  /** Whether all the descendants of the node are selected. */
  descendantsAllSelected(node: ItemFlatNode): boolean {
    const descendants = this.treeControl.getDescendants(node);
    const descAllSelected = descendants.every(child =>
      this.checklistSelection.isSelected(child)
    );
    return descAllSelected;
  }

  /** Whether part of the descendants are selected */
  descendantsPartiallySelected(node: ItemFlatNode): boolean {
    const descendants = this.treeControl.getDescendants(node);
    const result = descendants.some(child => this.checklistSelection.isSelected(child));
    return result && !this.descendantsAllSelected(node);
  }

  /** Toggle the to-do item selection. Select/deselect all the descendants node */
  ItemSelectionToggle(node: ItemFlatNode): void {
    this.checklistSelection.toggle(node);
    const descendants = this.treeControl.getDescendants(node);
    this.checklistSelection.isSelected(node) ? this.checklistSelection.select(...descendants) : this.checklistSelection.deselect(...descendants);
    // Force update for the parent
    descendants.every(child =>
      this.checklistSelection.isSelected(child)
    );
    this.checkAllParentsSelection(node);

  }

  /** Toggle a leaf to-do item selection. Check all the parents to see if they changed */
  LeafItemSelectionToggle(node: ItemFlatNode): void {
    this.checklistSelection.toggle(node);
    this.checkAllParentsSelection(node);
  }

  /* Checks all the parents when a leaf node is selected/unselected */
  checkAllParentsSelection(node: ItemFlatNode): void {
    let parent: ItemFlatNode | null = this.getParentNode(node);
    while (parent) {
      this.checkRootNodeSelection(parent);
      parent = this.getParentNode(parent);
    }
  }

  /** Check root node checked state and change it accordingly */
  checkRootNodeSelection(node: ItemFlatNode): void {
    const nodeSelected = this.checklistSelection.isSelected(node);
    const descendants = this.treeControl.getDescendants(node);
    const descAllSelected = descendants.every(child =>
      this.checklistSelection.isSelected(child)
    );
    if (nodeSelected && !descAllSelected) {
      this.checklistSelection.deselect(node);
    } else if (!nodeSelected && descAllSelected) {
      this.checklistSelection.select(node);
    }
  }

  /* Get the parent node of a node */
  getParentNode(node: ItemFlatNode): ItemFlatNode | null {
    const currentLevel = this.getLevel(node);
    if (currentLevel < 1) {
      return null;
    }
    const startIndex = this.treeControl.dataNodes.indexOf(node) - 1;
    for (let i = startIndex; i >= 0; i--) {
      const currentNode = this.treeControl.dataNodes[i];
      if (this.getLevel(currentNode) < currentLevel) {
        return currentNode;
      }
    }
    return null;
  }


}
