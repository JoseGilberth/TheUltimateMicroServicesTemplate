import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import Swal from 'sweetalert2';
import { configuraciones } from '../../../../../environments/configuraciones';
import { UsuariosAdminDTO } from '../../../../_dto/usuarios/UsuariosAdmin.Dto';
import { UsuariosAdminFiltroDTO } from '../../../../_dto/usuarios/UsuariosAdminFiltro.Dto';
import { PageableDTO } from '../../../../_dto/_main/Pageable.Dto';
import { TableComponent } from '../../../../_interfaces/tables.component';
import { UsuariosAdminService } from '../../../../_servicios/usuarios/administradores/usuariosadmin.service';
import { UtilComponent } from '../../../../_shared/util.component';
import { BuscarUsuariosComponent } from './buscar/buscar.component';


@Component({
  templateUrl: 'usuarios.component.html',
  styleUrls: ['usuarios.component.css']
})
export class UsuariosAdminComponent extends TableComponent {

  public configuraciones: any;
  public totalItems: number = 0;
  public currentPage: number = 0;
  public isLoading: boolean = false;
  public cantidadAMostrar: number = 5;
  maxSize = 10;
  bsModalRef: BsModalRef;

  public usuariosAdminDTO: UsuariosAdminDTO[];
  public usuariosAdminFiltroDTO: UsuariosAdminFiltroDTO;
  @Output() callbackOnModelWindowClose: EventEmitter<null> = new EventEmitter();

  constructor(private usuariosAdminService: UsuariosAdminService
    , public utilComponent: UtilComponent
    , private router: Router
    , private modalService: BsModalService) {
    super();
    this.configuraciones = configuraciones;
  }

  ngOnInit() {
    this.usuariosAdminFiltroDTO = new UsuariosAdminFiltroDTO();
    this.listAll(this.currentPage, this.cantidadAMostrar);
  }


  /*
  ================================================================
                          OBTENER USUARIOS
  ================================================================
  */
  listAll(pagina: number, cantidad: number) {
    this.isLoading = true;
    this.usuariosAdminDTO = [];
    this.usuariosAdminService.obtenerTodos(this.usuariosAdminFiltroDTO, pagina, cantidad)
      .subscribe(resp => {
        this.isLoading = false;
        this.usuariosAdminDTO = resp.cuerpo.content;
        this.totalItems = resp.cuerpo.totalElements;
      }, (error: HttpErrorResponse) => {
        this.isLoading = false;
      });
  }
  pageChanged(event: any): void {
    this.listAll((event.page - 1), this.cantidadAMostrar);
  }
  rowsToShow(value) {
    this.cantidadAMostrar = value;
    this.listAll(0, this.cantidadAMostrar);
  }

  /*
  ================================================================
                          EDITAR USUARIO
  ================================================================
  */
  editar(usuario: UsuariosAdminDTO) {
    sessionStorage.setItem(configuraciones.static.usuario, JSON.stringify(usuario));
    this.router.navigate(["/usuarios/administradores/editar"]);
  }

  /*
  ================================================================
                          BORRAR USUARIO
  ================================================================
  */
  borrar(usuario: UsuariosAdminDTO) {
    Swal.fire({
      title: 'Borrar usuario?',
      text: "Esta accion no puede ser removida!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, borrar usuario!',
      showLoaderOnConfirm: true,
      preConfirm: (login) => {
        return this.usuariosAdminService.borrar(usuario.id)
          .subscribe(resp => {
            this.utilComponent.showSweetAlert("Borrado", resp.mensaje, "success");
            this.listAll((this.currentPage - 1), this.cantidadAMostrar);
          }, (error: HttpErrorResponse) => {
            this.utilComponent.showSweetAlert("Error", this.utilComponent.trataErrores(error), "error");
          });
      },
      allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
      if (result.value) {
      }
    })
  }

  /*
  ================================================================
                          CREAR USUARIO
  ================================================================
  */
  nuevo() {
    this.router.navigate(["/usuarios/administradores/crear"]);
  }


  /*
  ================================================================
                          BUSCAR USUARIOS
  ================================================================
  */
  buscar() {
    const modalOptions = {
      initialState: {
        usuariosAdminFiltroDTO: this.usuariosAdminFiltroDTO
      },
      class: 'modal-lg'
    };
    this.bsModalRef = this.modalService.show(BuscarUsuariosComponent, modalOptions);
    this.bsModalRef.content.onClose = new Subject<boolean>();
    this.bsModalRef.content.onClose.subscribe((result: boolean) => {
      if (result) {
        this.listAll((this.currentPage - 1), this.cantidadAMostrar);
      }
    });
  }

  eliminarBusqueda() {
    this.utilComponent.cleanProperties(this.usuariosAdminFiltroDTO);
    this.listAll((this.currentPage - 1), this.cantidadAMostrar);
  }

}
