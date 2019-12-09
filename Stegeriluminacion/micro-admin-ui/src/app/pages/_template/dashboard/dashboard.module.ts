import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MdModule } from '../../_template/md/md.module';
//import { MaterialModule } from '../app.module';

import { SharedModule } from '../../../_shared/shared.module';

import { DashboardComponent } from './dashboard.component';
import { DashboardRoutes } from './dashboard.routing';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(DashboardRoutes),
        FormsModule,
        MdModule,
        SharedModule
    ],
    declarations: [DashboardComponent]
})

export class DashboardModule {}
