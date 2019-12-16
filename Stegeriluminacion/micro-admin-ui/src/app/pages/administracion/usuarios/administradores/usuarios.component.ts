import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { configuraciones } from '../../../../../environments/configuraciones';
import { UsuariosPublicosDTO } from '../../../../_dto/usuarios/UsuariosPublicos.Dto';
import { UsuariosPublicosFiltroDTO } from '../../../../_dto/usuarios/UsuariosPublicosFiltroDTO';
import { PageableDTO } from '../../../../_dto/_main/Pageable.Dto';
import { TableComponent } from '../../../../_interfaces/tables.component';
import { UtilComponent } from '../../../../_shared/util.component';
import { BuscarUsuariosComponent } from './buscar/buscar.component';
import Swal from 'sweetalert2';
import { ToastrService } from 'ngx-toastr';
import { UsuariosPublicosService } from '../../../../_servicios/usuarios/administradores/usuariosadmin.service';

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
  bsModalRef: BsModalRef;

  public usuariosPublicosDTO: UsuariosPublicosDTO[];
  public usuariosPublicosFiltroDTO: UsuariosPublicosFiltroDTO;
  @Output() callbackOnModelWindowClose: EventEmitter<null> = new EventEmitter();

  constructor(private usuariosPublicosService: UsuariosPublicosService
    , public utilComponent: UtilComponent
    , private router: Router
    , private modalService: BsModalService
    , private toastr: ToastrService) {
    super();
    this.configuraciones = configuraciones;
  }

  ngOnInit() {
    this.usuariosPublicosFiltroDTO = new UsuariosPublicosFiltroDTO();
    this.listAll(this.currentPage, this.cantidadAMostrar);
  }


  /*
  ================================================================
                          OBTENER USUARIOS
  ================================================================
  */
  listAll(pagina: number, cantidad: number) {
    this.isLoading = true;
    this.usuariosPublicosDTO = [];
    this.usuariosPublicosService.obtenerTodos(this.usuariosPublicosFiltroDTO, pagina, cantidad)
      .subscribe(resp => {
        this.isLoading = false;
        let pageable: PageableDTO = <PageableDTO>resp.cuerpo;
        this.usuariosPublicosDTO = pageable.content;
        this.totalItems = pageable.totalElements;
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
  editar(usuario: UsuariosPublicosDTO) {
    sessionStorage.setItem(configuraciones.static.usuario, JSON.stringify(usuario));
    this.router.navigate(["/usuarios/administradores/editar"]);
  }

  /*
  ================================================================
                          BORRAR USUARIO
  ================================================================
  */
  borrar(usuario: UsuariosPublicosDTO) {
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
        return this.usuariosPublicosService.borrar(usuario.id)
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
        usuariosPublicosFiltroDTO: this.usuariosPublicosFiltroDTO
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
    this.utilComponent.cleanProperties(this.usuariosPublicosFiltroDTO);
    this.listAll((this.currentPage - 1), this.cantidadAMostrar);
  }

}
