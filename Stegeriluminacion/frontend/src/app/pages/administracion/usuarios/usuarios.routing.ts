import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const UsuariosRoutes: Routes = [
  {
    path: '', data: { title: 'Usuarios' },
    children: [
      { path: '', redirectTo: 'publicos' },
      { path: 'publicos', loadChildren: () => import('./publicos/usuarios.module').then(m => m.UsuariosModule) },
      { path: 'administradores', loadChildren: () => import('./administradores/usuarios.module').then(m => m.UsuariosModule) }
    ]
  }
];
@NgModule({
  imports: [RouterModule.forChild(UsuariosRoutes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { }
