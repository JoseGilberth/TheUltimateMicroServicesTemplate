import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
//import { MaterialModule } from '../app.module';
import { SharedModule } from './../../../_shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { FlexLayoutModule } from '@angular/flex-layout';

import { PagesRoutes } from './pages.routing';

import { PricingComponent } from './pricing/pricing.component';
import { LockComponent } from './lock/lock.component';

import { LoginComponent } from './../../_login/login.component';
import { RegisterComponent } from './../../_login/register.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(PagesRoutes),
    FormsModule,
    SharedModule,
    ReactiveFormsModule
  ],
  declarations: [
   // LoginComponent,
    //RegisterComponent,
    PricingComponent,
    LockComponent
  ]
})

export class PagesModule {}
