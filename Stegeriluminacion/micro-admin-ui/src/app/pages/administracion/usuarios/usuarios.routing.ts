import { Routes } from '@angular/router';
//import { CrearComponent } from './crear/crear.component';
//import { EditarComponent } from './editar/editar.component';
import { UsuariosComponent } from './usuarios.component';
import { PermissionGuardService } from 'src/app/_guards/permission-guard.service';


export const UsuariosRoutes: Routes = [
  {
    path: '', component: UsuariosComponent, pathMatch: 'full', data: { breadcrumb: "", titulo: "usuarios", permiso: 'Application/SF:SaludFiscal:Web:Principal:Usuarios:Modulo' }, canActivate: [PermissionGuardService]
  }/*,
 
  {
    path: '', children: [{ path: 'crear', component: CrearComponent, data: { breadcrumb: "Crear", titulo: "Nuevo Usuario", permiso: "Application/SF:SaludFiscal:Web:Principal:Usuarios:Crear" } }], canActivate: [PermissionGuardService]
  },
  {
    path: '', children: [{ path: 'editar', component: EditarComponent, data: { breadcrumb: "Editar", titulo: "Editar Usuario", permiso: "Application/SF:SaludFiscal:Web:Principal:Usuarios:Editar" } }], canActivate: [PermissionGuardService]
  }*/
];
