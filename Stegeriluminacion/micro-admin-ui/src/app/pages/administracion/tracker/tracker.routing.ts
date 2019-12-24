import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from '../../../_guards/auth-guard.service';
import { PermissionGuardService } from '../../../_guards/permission-guard.service';
import { TrackerComponent } from './tracker.component';


const TrackerRoutes: Routes = [
  {
    path: '', data: { title: 'Tracker' },
    children: [
      // MOSTRAR
      {
        path: '', component: TrackerComponent, pathMatch: 'full',
        data: { title: '', permiso: 'web:administracion:tracker:mostrar' },
        canActivate: [AuthGuardService, PermissionGuardService]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(TrackerRoutes)],
  exports: [RouterModule]
})
export class TrackerRoutingModule { }
