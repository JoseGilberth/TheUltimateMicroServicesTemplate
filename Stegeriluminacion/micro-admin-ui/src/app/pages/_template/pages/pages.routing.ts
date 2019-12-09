import { Routes } from '@angular/router';

import { PricingComponent } from './pricing/pricing.component';
import { LockComponent } from './lock/lock.component';

import { LoginComponent } from './../../_login/login.component';
import { RegisterComponent } from './../../_login/register.component';

export const PagesRoutes: Routes = [

    {
        path: '',
        children: [ {
            path: 'login',
            component: LoginComponent
        }, {
            path: 'lock',
            component: LockComponent
        }, {
            path: 'register',
            component: RegisterComponent
        }, {
            path: 'pricing',
            component: PricingComponent
        }]
    }
];
