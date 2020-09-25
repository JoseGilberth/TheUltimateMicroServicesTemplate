import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from '../../../_guards/auth-guard.service';
import { PermissionGuardService } from '../../../_guards/permission-guard.service';
import { LogComponent } from './log.component';


const LogRoutes: Routes = [
  {
    path: '', data: { title: 'Log' },
    children: [
      // MOSTRAR
      {
        path: '', component: LogComponent, pathMatch: 'full',
        data: { title: '', permiso: 'web:administracion:log:todos' },
        canActivate: [AuthGuardService, PermissionGuardService]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(LogRoutes)],
  exports: [RouterModule]
})
export class LogRoutingModule { }
