import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { BaseDatosComponent } from './basedatos.component';
import { AuthGuardService } from '../../../_guards/auth-guard.service';
import { PermissionGuardService } from '../../../_guards/permission-guard.service';

const routes: Routes = [
  {
    path: '', data: { title: 'Base de Datos' },
    children: [
      // MOSTRAR
      {
        path: '', component: BaseDatosComponent, pathMatch: 'full',
        data: { title: '', permiso: 'web:administracion:basedatos:todos' },
        canActivate: [AuthGuardService, PermissionGuardService]
      }
    ]
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BaseDatosRoutingModule { }
