import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


import { SharedModule } from 'src/app/_shared/shared.module';

import { PerfilesRoutes } from './perfiles.routing';
import { PerfilesComponent } from './perfiles.component';
import { CrearComponent } from './crear/crear.component';
import { FiltroComponent } from './filtro/filtro.component';
import { EditarComponent } from './editar/editar.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild( PerfilesRoutes ),
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ],
  entryComponents: [
    CrearComponent,
    FiltroComponent,
    EditarComponent
  ],
  declarations: [
      PerfilesComponent,
      CrearComponent,
      FiltroComponent,
      EditarComponent
  ]
})

export class PerfilesModule {}
