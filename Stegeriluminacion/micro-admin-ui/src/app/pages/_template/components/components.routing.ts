import { Routes } from '@angular/router';

import { ButtonsComponent } from './buttons/buttons.component';
import { GridSystemComponent } from './grid/grid.component';
import { IconsComponent } from './icons/icons.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { PanelsComponent } from './panels/panels.component';
import { SweetAlertComponent } from './sweetalert/sweetalert.component';
import { TypographyComponent } from './typography/typography.component';


export const ComponentsRoutes: Routes = [
    {
        path: '', children: [{ path: 'buttons', component: ButtonsComponent , data: { breadcrumb: 'Botones' } } ]
    }, 
    {
        path: '', children: [{ path: 'grid', component: GridSystemComponent, data: { breadcrumb: 'Grid' } }]
    }, 
    { 
        path: '', children: [{ path: 'icons', component: IconsComponent, data: { breadcrumb: 'Iconos' } }]
    },
    {
        path: '', children: [{ path: 'notifications', component: NotificationsComponent, data: { breadcrumb: 'Notificaciones' } }]
    }, 
    {
        path: '', children: [{ path: 'panels', component: PanelsComponent, data: { breadcrumb: 'Panel' } }]
    }, 
    {
        path: '', children: [{ path: 'sweet-alert', component: SweetAlertComponent, data: { breadcrumb: 'Alertas' } }]
    }, 
    {
        path: '', children: [{ path: 'typography', component: TypographyComponent, data: { breadcrumb: 'Tipografia' } }]
    }
];
