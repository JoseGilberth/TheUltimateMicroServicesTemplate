import { Component, OnInit } from '@angular/core';
import PerfectScrollbar from 'perfect-scrollbar';
import { UtilComponent } from 'src/app/_shared/util.component';

declare const $: any;

// Metadata
export interface RouteInfo {
    path: string;
    permiso: string;
    title: string;
    type: string;
    icontype: string;
    collapse?: string;
    children?: ChildrenItems[];
}

export interface ChildrenItems {
    path: string;
    title: string;
    ab?: string;
    type?: string;
    children?: ChildrenItems[];
}

// Menu Items
export const ROUTES: RouteInfo[] = [
    {
        path: '/usuarios',
        permiso: '',
        title: 'Usuarios',
        type: 'header',
        icontype: '',
        children: [
            { path: 'crear', title: 'Crear', ab: 'CR' },
            { path: 'editar', title: 'Editar', ab: 'ED' }
        ]
    },
    {
        path: '/perfiles',
        permiso: '',
        title: 'Perfiles',
        type: 'header',
        icontype: '',
        children: [
            { path: 'crear', title: 'Crear', ab: 'CR' },
            { path: 'editar', title: 'Editar', ab: 'ED' }
        ]
    },
    {
        path: '/dashboard',
        permiso: '',
        title: 'Dashboard',
        type: 'link',
        icontype: 'fa fa-tachometer'
    }
];

@Component({
    selector: 'app-sidebar-cmp',
    templateUrl: 'sidebar.component.html',
})

export class SidebarComponent implements OnInit {
    public menuItems: any[];
    public respuesta;
    constructor(
        public utilComponent: UtilComponent) {

    }


    isMobileMenu() {
        if ($(window).width() > 991) {
            return false;
        }
        return true;
    }

    ngOnInit() {
        this.menuItems = ROUTES.filter(menuItem => menuItem);
    }

    updatePS(): void {
        if (window.matchMedia(`(min-width: 960px)`).matches && !this.isMac()) {
            const elemSidebar = <HTMLElement>document.querySelector('.sidebar .sidebar-wrapper');
            let ps = new PerfectScrollbar(elemSidebar, { wheelSpeed: 2, suppressScrollX: true });
        }
    }

    isMac(): boolean {
        let bool = false;
        if (navigator.platform.toUpperCase().indexOf('MAC') >= 0 || navigator.platform.toUpperCase().indexOf('IPAD') >= 0) {
            bool = true;
        }
        return bool;
    }

    ocultar(titulo) {
        let mostrar = false;

        if (titulo === 'Órdenes de Compra' || titulo === 'Facturas') {
            if (!this.respuesta) {
                return true;
            }
        }

        return false;
    }
}
