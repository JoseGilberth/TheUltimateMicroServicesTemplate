import { Routes, RouterModule } from '@angular/router';
//import { CrearComponent } from './crear/crear.component';
//import { EditarComponent } from './editar/editar.component';
import { UsuariosComponent } from './usuarios.component';
import { PermissionGuardService } from '../../../_guards/permission-guard.service';
import { NgModule } from '@angular/core';


const UsuariosRoutes: Routes = [
  {
    path: '',
    component: UsuariosComponent,
    data: {
      title: 'usuarios'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(UsuariosRoutes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { }
