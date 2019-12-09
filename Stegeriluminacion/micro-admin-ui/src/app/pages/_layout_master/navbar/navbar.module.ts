import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from './navbar.component';
import { MatButtonModule } from '@angular/material';
import {MatGridListModule} from '@angular/material/grid-list';
@NgModule({
    imports: [ RouterModule, CommonModule, MatButtonModule, MatGridListModule ],
    declarations: [ NavbarComponent ],
    exports: [ NavbarComponent ]
})

export class NavbarModule {}
