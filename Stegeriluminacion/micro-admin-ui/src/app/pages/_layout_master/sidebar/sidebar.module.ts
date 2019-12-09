import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SidebarComponent } from './sidebar.component';

@NgModule({
    imports: [RouterModule, CommonModule],
    declarations: [SidebarComponent],
    exports: [SidebarComponent]
})

export class SidebarModule { }
