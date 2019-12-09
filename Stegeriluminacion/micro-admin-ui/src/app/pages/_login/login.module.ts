import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SharedModule } from './../../_shared/shared.module';
import { LoginComponent } from './login.component';
// import { FlexLayoutModule } from '@angular/flex-layout';
import { LoginRoutes } from './login.routing';
import { RegisterComponent } from './register.component';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(LoginRoutes),
    FormsModule,
    SharedModule,
    ReactiveFormsModule
  ],
  declarations: [
    LoginComponent,
    RegisterComponent
  ]
})

export class LoginModule { }
