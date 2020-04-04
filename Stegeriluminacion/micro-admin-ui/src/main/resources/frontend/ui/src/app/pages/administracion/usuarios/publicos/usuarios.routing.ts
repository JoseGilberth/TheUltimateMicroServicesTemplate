import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from '../../../../_guards/auth-guard.service';
import { PermissionGuardService } from '../../../../_guards/permission-guard.service';
import { CrearUsuarioComponent } from './crear/crear.component';
import { EditarUsuarioComponent } from './editar/editar.component';
import { UsuariosComponent } from './usuarios.component';


const UsuariosRoutes: Routes = [
  {
    path: '', data: { title: 'Publicos' },
    children: [
      // MOSTRAR
      {
        path: '', component: UsuariosComponent, pathMatch: 'full',
        data: { title: '', permiso: 'web:administracion:usuarios:publicos:mostrar' },
        canActivate: [AuthGuardService, PermissionGuardService]
      },
      // CREAR
      {
        path: 'crear', component: CrearUsuarioComponent,
        data: { title: 'Crear', permiso: 'web:administracion:usuarios:publicos:crear' },
        canActivate: [AuthGuardService, PermissionGuardService]
      },
      // EDITAR
      {
        path: 'editar', component: EditarUsuarioComponent,
        data: { title: 'Editar', permiso: 'web:administracion:usuarios:publicos:actualizar' },
        canActivate: [AuthGuardService, PermissionGuardService]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(UsuariosRoutes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { }
