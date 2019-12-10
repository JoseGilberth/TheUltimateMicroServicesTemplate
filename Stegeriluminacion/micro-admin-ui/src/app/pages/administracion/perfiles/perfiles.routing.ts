import { Routes } from '@angular/router';

import { PerfilesComponent } from './perfiles.component';
import { CrearComponent } from './crear/crear.component';
import { EditarComponent } from './editar/editar.component';

export const PerfilesRoutes: Routes = [
    {
      path: '', component: PerfilesComponent, pathMatch: 'full', data: { breadcrumb: "", titulo: "Perfiles" },
    },
    {
      path: '', children: [{ path: 'crear', component: CrearComponent, data: { breadcrumb: "Crear", titulo: "Nuevo Perfil" } }]
    },
    {
      path: '', children: [{ path: 'editar', component: EditarComponent, data: { breadcrumb: "Editar", titulo: "Editar Perfil" } }]
    }
];
