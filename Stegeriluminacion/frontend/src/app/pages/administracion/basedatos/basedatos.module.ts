import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ChartsModule } from 'ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

import { BaseDatosComponent } from './basedatos.component';
import { BaseDatosRoutingModule } from './basedatos-routing.module';

@NgModule({
  imports: [
    FormsModule,
    BaseDatosRoutingModule,
    ChartsModule,
    BsDropdownModule,
    ButtonsModule.forRoot()
  ],
  declarations: [ BaseDatosComponent ]
})
export class BaseDatosModule { }
