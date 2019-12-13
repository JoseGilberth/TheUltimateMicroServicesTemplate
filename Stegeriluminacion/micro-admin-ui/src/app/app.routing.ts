import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Import Containers

import { LoginComponent } from './pages/_login/login.component';
import { DefaultLayoutComponent } from './pages/_layout_master';
import { P500Component } from './pages/administracion/error/500.component';
import { P404Component } from './pages/administracion/error/404.component';
import { RegisterComponent } from './pages/administracion/register/register.component';
import { PermissionGuardService } from './_guards/permission-guard.service';
import { AuthGuardService } from './_guards/auth-guard.service';

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
      { path: 'usuarios/publicos', loadChildren: () => import('./pages/administracion/usuarios/usuarios.module').then(m => m.UsuariosModule) },
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
