import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from '../../../_guards/auth-guard.service';
import { PermissionGuardService } from '../../../_guards/permission-guard.service';
import { SesionesComponent } from './sesiones.component';
 
const SesionesRoutes: Routes = [
  {
    path: '', data: { title: 'Sesiones' },
    children: [
      // MOSTRAR
      {
        path: '', component: SesionesComponent, pathMatch: 'full',
        data: { title: '' , permiso: 'web:administracion:sesiones:mostrar' },
        canActivate: [AuthGuardService, PermissionGuardService]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(SesionesRoutes)],
  exports: [RouterModule]
})
export class SesionesRoutingModule { }
