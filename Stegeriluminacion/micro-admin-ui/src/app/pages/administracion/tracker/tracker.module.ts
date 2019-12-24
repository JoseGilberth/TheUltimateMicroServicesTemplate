import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AlertModule } from 'ngx-bootstrap/alert';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { TreeviewModule } from 'ngx-treeview';
import { BuscarTrackerComponent } from './buscar/buscar.component';
import { TrackerRoutingModule } from './tracker.routing';
import { TrackerComponent } from './tracker.component';

@NgModule({
  imports: [
    CommonModule,
    TrackerRoutingModule,
    FormsModule,
    BsDropdownModule.forRoot(),
    PopoverModule.forRoot(),
    AlertModule.forRoot(),
    PaginationModule.forRoot(),
    ModalModule.forRoot(),
    TreeviewModule.forRoot(),
    ReactiveFormsModule
  ],
  entryComponents: [
    BuscarTrackerComponent
  ],
  declarations: [
    TrackerComponent,
    BuscarTrackerComponent
  ]
})

export class TrackerModule { }
