import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CrearUsuarioComponent } from './crear/crear.component';
import { EditarUsuarioComponent } from './editar/editar.component';
import { UsuariosComponent } from './usuarios.component';


const UsuariosRoutes: Routes = [
  {
    path: '', data: { title: 'Usuarios' },
    children: [
      { path: '', component: UsuariosComponent, pathMatch: 'full', data: { title: '' } },
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
