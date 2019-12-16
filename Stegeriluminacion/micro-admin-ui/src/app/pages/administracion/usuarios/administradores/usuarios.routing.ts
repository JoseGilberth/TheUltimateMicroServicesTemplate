import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CrearUsuarioComponent } from './crear/crear.component';
import { EditarUsuarioComponent } from './editar/editar.component';
import { UsuariosAdminComponent } from './usuarios.component';

const UsuariosRoutes: Routes = [
  {
    path: '', data: { title: 'Administradores' },
    children: [
      { path: '', component: UsuariosAdminComponent, pathMatch: 'full', data: { title: '' } },
      { path: 'crear', component: CrearUsuarioComponent, data: { title: 'Crear' } },
      { path: 'editar', component: EditarUsuarioComponent, data: { title: 'Editar' } }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(UsuariosRoutes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { }
