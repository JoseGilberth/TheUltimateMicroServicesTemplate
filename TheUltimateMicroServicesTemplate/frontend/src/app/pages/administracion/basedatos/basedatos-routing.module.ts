import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { BaseDatosComponent } from './basedatos.component';

const routes: Routes = [
  {
    path: '',
    component: BaseDatosComponent,
    data: {
      title: 'Base de datos'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BaseDatosRoutingModule {}
