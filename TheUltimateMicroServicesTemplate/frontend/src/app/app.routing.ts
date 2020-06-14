import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { P403Component } from './pages/administracion/error/403.component';
import { P404Component } from './pages/administracion/error/404.component';
import { P500Component } from './pages/administracion/error/500.component';
import { RegisterComponent } from './pages/administracion/register/register.component';
import { DefaultLayoutComponent } from './pages/_layout_master';
// Import Containers
import { LoginComponent } from './pages/_login/login.component';
import { AuthGuardService } from './_guards/auth-guard.service';
import { PermissionGuardService } from './_guards/permission-guard.service';


export const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: '', component: DefaultLayoutComponent,
    canActivate: [AuthGuardService, PermissionGuardService],
    data: {
      title: 'Home'
    },
    children: [
      { path: '', loadChildren: './pages/administracion/dashboard/dashboard.module#DashboardModule' },
      { path: 'usuarios', loadChildren: () => import('./pages/administracion/usuarios/usuarios.module').then(m => m.UsuariosModule) },
      { path: 'log', loadChildren: () => import('./pages/administracion/log/log.module').then(m => m.LogModule) },
      { path: 'sesiones', loadChildren: () => import('./pages/administracion/sesiones/sesiones.module').then(m => m.SesionesModule) },
      { path: 'basedatos', loadChildren: () => import('./pages/administracion/basedatos/basedatos.module').then(m => m.BaseDatosModule) },

      { path: 'base', loadChildren: () => import('./pages/administracion/base/base.module').then(m => m.BaseModule) },
      { path: 'buttons', loadChildren: () => import('./pages/administracion/buttons/buttons.module').then(m => m.ButtonsModule) },
      { path: 'charts', loadChildren: () => import('./pages/administracion/chartjs/chartjs.module').then(m => m.ChartJSModule) },
      { path: 'dashboard', loadChildren: () => import('./pages/administracion/dashboard/dashboard.module').then(m => m.DashboardModule) },
      { path: 'icons', loadChildren: () => import('./pages/administracion/icons/icons.module').then(m => m.IconsModule) },
      { path: 'notifications', loadChildren: () => import('./pages/administracion/notifications/notifications.module').then(m => m.NotificationsModule) },
      { path: 'theme', loadChildren: () => import('./pages/administracion/theme/theme.module').then(m => m.ThemeModule) },
      { path: 'widgets', loadChildren: () => import('./pages/administracion/widgets/widgets.module').then(m => m.WidgetsModule) }
    ]
  },
  {
    path: '404',
    component: P404Component,
    data: {
      title: 'Page 404'
    }
  },
  {
    path: '403',
    component: P403Component,
    data: {
      title: 'Page 403'
    }
  },
  {
    path: '500',
    component: P500Component,
    data: {
      title: 'Page 500'
    }
  },
  {
    path: 'login',
    component: LoginComponent,
    data: {
      title: 'Login Page'
    }
  },
  {
    path: 'register',
    component: RegisterComponent,
    data: {
      title: 'Register Page'
    }
  },
  { path: '**', component: P404Component }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
