import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Output } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { LogDTO } from '../../../_dto/log/Log.Dto';
import { LogFiltroDTO } from '../../../_dto/log/LogFiltro.Dto';
import { PageableDTO } from '../../../_dto/_main/Pageable.Dto';
import { LogService } from '../../../_servicios/log/log.service';
import { UtilComponent } from '../../../_shared/util.component';
import { BuscarLogComponent } from './buscar/buscar.component';

@Component({
  templateUrl: 'log.component.html',
  styleUrls: ['log.component.css']
})
export class LogComponent {

  public totalItems: number = 0;
  public currentPage: number = 0;
  public isLoading: boolean = false;
  public cantidadAMostrar: number = 5;
  maxSize = 10;
  bsModalRef: BsModalRef;

  public logDTO: LogDTO[];
  public logFiltroDTO: LogFiltroDTO;
  @Output() callbackOnModelWindowClose: EventEmitter<null> = new EventEmitter();

  constructor(private logService: LogService
    , public utilComponent: UtilComponent
    , private modalService: BsModalService) {
  }

  ngOnInit() {
    this.logFiltroDTO = new LogFiltroDTO();
    this.listAll(this.currentPage, this.cantidadAMostrar);
  }


  /*
  ================================================================
                          OBTENER USUARIOS
  ================================================================
  */
  listAll(pagina: number, cantidad: number) {
    this.isLoading = true;
    this.logDTO = [];
    this.logService.obtenerTodos(this.logFiltroDTO, pagina, cantidad)
      .subscribe(resp => {
        this.isLoading = false;
        this.logDTO = resp.cuerpo.content;
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
                          BUSCAR USUARIOS
  ================================================================
  */
  buscar() {
    const modalOptions = {
      initialState: {
        logFiltroDTO: this.logFiltroDTO
      },
      class: 'modal-lg'
    };
    this.bsModalRef = this.modalService.show(BuscarLogComponent, modalOptions);
    this.bsModalRef.content.onClose = new Subject<boolean>();
    this.bsModalRef.content.onClose.subscribe((result: boolean) => {
      if (result) {
        this.listAll((this.currentPage - 1), this.cantidadAMostrar);
      }
    });
  }

  eliminarBusqueda() {
    this.utilComponent.cleanProperties(this.logFiltroDTO);
    this.listAll((this.currentPage - 1), this.cantidadAMostrar);
  }

}
