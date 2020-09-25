import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Output } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { configuraciones } from '../../../../environments/configuraciones';
import { SesionesDTO } from '../../../_dto/sesiones/Sesiones.Dto';
import { SesionesFiltroDTO } from '../../../_dto/sesiones/SesionesFiltro.Dto';
import { PageableDTO } from '../../../_dto/_main/Pageable.Dto';
import { SesionesService } from '../../../_servicios/sesiones/sesiones.service';
import { UtilComponent } from '../../../_shared/util.component';
import { BuscarSesionesComponent } from './buscar/buscar.component';
import Swal from 'sweetalert2';

@Component({
  templateUrl: 'sesiones.component.html',
  styleUrls: ['sesiones.component.css']
})
export class SesionesComponent {

  public configuraciones: any;
  public totalItems: number = 0;
  public currentPage: number = 0;
  public isLoading: boolean = false;
  maxSize = 10;
  public cantidadAMostrar: number = 5;
  bsModalRef: BsModalRef;

  public sesionesDTO: SesionesDTO[];
  public sesionesFiltroDTO: SesionesFiltroDTO;
  @Output() callbackOnModelWindowClose: EventEmitter<null> = new EventEmitter();

  constructor(private SesionesService: SesionesService
    , public utilComponent: UtilComponent
    , private modalService: BsModalService) {
    this.configuraciones = configuraciones;
  }

  ngOnInit() {
    this.sesionesFiltroDTO = new SesionesFiltroDTO();
    this.listAll(this.currentPage, this.cantidadAMostrar);
  }


  /*
  ================================================================
                          OBTENER USUARIOS
  ================================================================
  */
  listAll(pagina: number, cantidad: number) {
    this.isLoading = true;
    this.sesionesDTO = [];
    this.SesionesService.obtenerTodos(this.sesionesFiltroDTO, pagina, cantidad)
      .subscribe(resp => {
        this.isLoading = false;
        this.sesionesDTO = resp.cuerpo.content;
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
                          BORRAR USUARIO
  ================================================================
  */
  cerrarSesion(sesion: SesionesDTO) {
    Swal.fire({
      title: 'Cerrar sesion?',
      text: "Esta accion cerrara la sesión del usuario seleccionado!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, cerrar sesion!',
      showLoaderOnConfirm: true,
      preConfirm: (login) => {
        return this.SesionesService.borrar(sesion.token)
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
                          BUSCAR USUARIOS
  ================================================================
  */
  buscar() {
    const modalOptions = {
      initialState: {
        sesionesFiltroDTO: this.sesionesFiltroDTO
      },
      class: 'modal-lg'
    };
    this.bsModalRef = this.modalService.show(BuscarSesionesComponent, modalOptions);
    this.bsModalRef.content.onClose = new Subject<boolean>();
    this.bsModalRef.content.onClose.subscribe((result: boolean) => {
      if (result) {
        this.listAll((this.currentPage - 1), this.cantidadAMostrar);
      }
    });
  }

  eliminarBusqueda() {
    this.utilComponent.cleanProperties(this.sesionesFiltroDTO);
    this.listAll((this.currentPage - 1), this.cantidadAMostrar);
  }




}
