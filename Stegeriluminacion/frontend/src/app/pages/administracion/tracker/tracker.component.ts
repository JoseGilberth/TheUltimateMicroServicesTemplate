import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Output } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { configuraciones } from '../../../../environments/configuraciones';
import { TrackerDTO } from '../../../_dto/tracker/tracker.Dto';
import { TrackerFiltroDTO } from '../../../_dto/tracker/trackerFiltro.Dto';
import { PageableDTO } from '../../../_dto/_main/Pageable.Dto';
import { TrackerService } from '../../../_servicios/tracker/tracker.service';
import { UtilComponent } from '../../../_shared/util.component';
import { BuscarTrackerComponent } from './buscar/buscar.component';

@Component({
  templateUrl: 'tracker.component.html',
  styleUrls: ['tracker.component.css']
})
export class TrackerComponent {

  public configuraciones: any;
  public totalItems: number = 0;
  public currentPage: number = 0;
  public isLoading: boolean = false;
  public cantidadAMostrar: number = 5;
  bsModalRef: BsModalRef;

  public trackerDTO: TrackerDTO[];
  public trackerFiltroDTO: TrackerFiltroDTO;
  @Output() callbackOnModelWindowClose: EventEmitter<null> = new EventEmitter();

  constructor(private TrackerService: TrackerService
    , public utilComponent: UtilComponent
    , private modalService: BsModalService) {
    this.configuraciones = configuraciones;
  }

  ngOnInit() {
    this.trackerFiltroDTO = new TrackerFiltroDTO();
    this.listAll(this.currentPage, this.cantidadAMostrar);
  }


  /*
  ================================================================
                          OBTENER USUARIOS
  ================================================================
  */
  listAll(pagina: number, cantidad: number) {
    this.isLoading = true;
    this.trackerDTO = [];
    this.TrackerService.obtenerTodos(this.trackerFiltroDTO, pagina, cantidad)
      .subscribe(resp => {
        this.isLoading = false;
        let pageable: PageableDTO = <PageableDTO>resp.cuerpo;
        this.trackerDTO = pageable.content;
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
                          BUSCAR USUARIOS
  ================================================================
  */
  buscar() {
    const modalOptions = {
      initialState: {
        TrackerFiltroDTO: this.trackerFiltroDTO
      },
      class: 'modal-lg'
    };
    this.bsModalRef = this.modalService.show(BuscarTrackerComponent, modalOptions);
    this.bsModalRef.content.onClose = new Subject<boolean>();
    this.bsModalRef.content.onClose.subscribe((result: boolean) => {
      if (result) {
        this.listAll((this.currentPage - 1), this.cantidadAMostrar);
      }
    });
  }

  eliminarBusqueda() {
    this.utilComponent.cleanProperties(this.trackerFiltroDTO);
    this.listAll((this.currentPage - 1), this.cantidadAMostrar);
  }

}
