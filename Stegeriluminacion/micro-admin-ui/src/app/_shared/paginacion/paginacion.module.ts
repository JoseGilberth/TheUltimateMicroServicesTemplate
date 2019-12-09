import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule, MatIconModule, MatInputModule } from '@angular/material';
import { PaginacionComponent } from './paginacion.component';

@NgModule({
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatInputModule

    ],
    declarations: [
        PaginacionComponent
    ],
    exports: [
        PaginacionComponent
    ]
})
export class PaginacionModule { }