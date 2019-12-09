import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './pages/_layout_master/admin/admin-layout.component';
import { AuthLayoutComponent } from './pages/_layout_master/auth/auth-layout.component';
import { AuthGuardService } from './_guards/auth-guard.service';
import { PermissionGuardService } from './_guards/permission-guard.service';


export const AppRoutes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: '',
    component: AdminLayoutComponent,
    canActivate: [AuthGuardService, PermissionGuardService],
    children: [
      { path: '', loadChildren: './pages/administracion/dashboard/dashboard.module#DashboardModule', data: { breadcrumb: 'Dashboard' }} 
     // , { path: 'usuarios', loadChildren: './pages/administracion/principal/usuarios/usuarios.module#UsuariosModule', data: { breadcrumb: 'Usuarios', permiso: 'Application/SF:SaludFiscal:Web:Principal:Usuarios:Modulo' }, canActivate: [PermissionGuardService] }
    ]
  },
  {
    path: '', component: AuthLayoutComponent,
    children: [
      { path: 'login', loadChildren: './pages/_login/login.module#LoginModule' },
      { path: 'pages', loadChildren: './pages/_template/pages/pages.module#PagesModule', data: { breadcrumb: 'Páginas' } }
    ]
  },
  { path: '**', redirectTo: 'login' }
];
