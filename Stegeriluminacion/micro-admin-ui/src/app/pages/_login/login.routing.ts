import { Routes } from '@angular/router';
import { LoginComponent } from './login.component';
import { RegisterComponent } from './register.component';

export const LoginRoutes: Routes = [
  { path: '', component: LoginComponent, pathMatch: 'full' },
  { path: 'registro', component: RegisterComponent, pathMatch: 'full' }
];
