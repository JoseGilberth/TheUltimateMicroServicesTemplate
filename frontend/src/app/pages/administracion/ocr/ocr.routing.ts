import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from '../../../_guards/auth-guard.service';
import { PermissionGuardService } from '../../../_guards/permission-guard.service';
import { OcrComponent } from './ocr.component';


const LogRoutes: Routes = [
  {
    path: '', data: { title: 'OCR' },
    children: [
      // MOSTRAR
      {
        path: '', component: OcrComponent, pathMatch: 'full',
        data: { title: '' },
        canActivate: [AuthGuardService, PermissionGuardService]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(LogRoutes)],
  exports: [RouterModule]
})
export class OcrRoutingModule { }
