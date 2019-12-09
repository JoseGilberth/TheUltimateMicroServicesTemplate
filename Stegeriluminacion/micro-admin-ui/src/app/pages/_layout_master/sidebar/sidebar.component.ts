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
        permiso: 'Application/SF:SaludFiscal:Web:Principal:Usuarios:Modulo',
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
        permiso: 'Application/SF:SaludFiscal:Web:Principal:Perfiles:Modulo',
        title: 'Perfiles',
        type: 'header',
        icontype: '',
        children: [
            { path: 'crear', title: 'Crear', ab: 'CR' },
            { path: 'editar', title: 'Editar', ab: 'ED' }
        ]
    },
    {
        path: '/empresas',
        permiso: 'Application/SF:SaludFiscal:Web:Principal:Empresas:Modulo',
        title: 'Empresas',
        type: 'header',
        icontype: ''
    },
    {
        path: '/dashboard',
        permiso: '',
        title: 'Dashboard',
        type: 'link',
        icontype: 'fa fa-tachometer'
    },
    {
        path: '/portalProveedores',
        permiso: 'Application/SF:SaludFiscal:Web:ProveedoresPortal:Expediente:Ver',
        title: 'Portal proveedores',
        type: 'sub',
        icontype: 'fa fa-id-card',
        collapse: 'portalProveedores',
        children: [
            { path: 'expediente', title: 'Expediente', ab: 'E' },
            {
                path: 'facturas', title: 'Facturas', ab: 'F', type: 'header-sub',
                children: [
                    {
                        path: 'detallesFact', title: 'Detalles factura', type: 'header-sub'
                    }
                ]
            },
            { path: 'ordenesCompra', title: 'Órdenes de compra', ab: 'OC' }
        ]
    },
    {
        path: '/repositorio',
        permiso: 'Application/SF:SaludFiscal:Web:Repositorio:Modulo',
        title: 'Repositorio',
        type: 'sub',
        icontype: 'fa fa-archive',
        collapse: 'repositorio',
        children: [
            { path: 'complementosEmitidos', title: 'Complementos emitidos', ab: 'CE' },
            { path: 'complementosRecibidos', title: 'Complementos recibidos', ab: 'CR' },
            { path: 'declaraciones', title: 'Declaraciones', ab: 'DE' },
            { path: 'facturasEmitidas', title: 'Facturas emitidas', ab: 'FE' },
            { path: 'facturasRecibidas', title: 'Facturas recibidas', ab: 'FR' },
            { path: 'historialFacturas', title: 'Historial de facturas', ab: 'HF' },
            { path: 'nominasEmitidas', title: 'Nóminas emitidas', ab: 'NE' }
        ]
    },
    {
        path: '/buzontributario',
        permiso: 'Application/SF:SaludFiscal:Web:BuzonTributario:Modulo',
        title: 'Buzón tributario',
        type: 'link',
        icontype: 'fa fa-envelope',

    },
    {
        path: '/listanegra',
        permiso: 'Application/SF:SaludFiscal:Web:BuzonTributario:Modulo',
        title: 'Lista negra',
        type: 'sub',
        icontype: 'fa fa-clipboard',
        collapse: 'listanegra',
        children: [
            { path: 'busquedarfc', title: 'Búsqueda RFC', ab: 'RFC' },
            { path: 'clientesproveedores', title: 'Clientes proveedores', ab: 'CP' },
            { path: 'config', title: 'Configuraciones', ab: 'C' }
        ]
    },
    {
        path: '/clinica',
        permiso: 'Application/SF:SaludFiscal:Web:Repositorio:Modulo',//'Application/SF:SaludFiscal:Web:ClinicaFiscal:Modulo',
        title: 'Clínica físcal',
        type: 'sub',
        icontype: 'fa fa-heartbeat',
        collapse: 'clinicafiscal',
        children: [
            { path: 'carga', title: 'Carga de archivo', ab: 'CG' },
            { path: 'reporte', title: 'Resultado de cruce de facturas', ab: 'RE' }
        ]
    },
    {
        path: '/erp',
        title: 'ERP',
        permiso: 'Application/SF:SaludFiscal:Web:ERP:Modulo',
        type: 'multi-sub',
        icontype: 'fa fa-code-fork',
        children: [
            {
                path: 'articulos', title: 'Artículos', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoArticulo', title: 'Nuevo artículo', type: 'header-sub'
                    },
                    {
                        path: 'editarArticulo', title: 'Editar artículo', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'monedas', title: 'Monedas', type: 'header-sub',
                children: [
                    {
                        path: 'nuevaMoneda', title: 'Nueva moneda', type: 'header-sub'
                    },
                    {
                        path: 'editarMoneda', title: 'Editar moneda', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'departamentos', title: 'Departamentos', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoDepartamento', title: 'Nuevo departamento', type: 'header-sub'
                    },
                    {
                        path: 'editarDepartamento', title: 'Editar departamento', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'cuentasERP', title: 'Cuentas ERP', type: 'header-sub',
                children: [
                    {
                        path: 'nuevaCuentaERP', title: 'Nueva cuenta ERP', type: 'header-sub'
                    },
                    {
                        path: 'editarCuentaERP', title: 'Editar cuenta ERP', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'empleados', title: 'Empleados', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoEmpleado', title: 'Nuevo empleado', type: 'header-sub'
                    },
                    {
                        path: 'editarEmpleado', title: 'Editar empleado', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'codigoCosto', title: 'Código costo', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoCodigoCosto', title: 'Nuevo código costo', type: 'header-sub'
                    },
                    {
                        path: 'editarCodigoCosto', title: 'Editar código costo', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'unidadMedida', title: 'Unidades de medida', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoUnidadMedida', title: 'Nuevo unidad de medida', type: 'header-sub'
                    },
                    {
                        path: 'editarUnidadMedida', title: 'Editar unidad de medida', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'clientes', title: 'Clientes', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoCliente', title: 'Nuevo cliente', type: 'header-sub'
                    },
                    {
                        path: 'editarCliente', title: 'Editar cliente', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'impuestos', title: 'Impuestos', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoImpuesto', title: 'Nuevo impuesto', type: 'header-sub'
                    },
                    {
                        path: 'editarImpuesto', title: 'Editar impuesto', type: 'header-sub'
                    },
                    {
                        path: 'configImpuestos', title: 'Configurar impuesto', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'cuentaPago', title: 'Cuenta pago', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoCuentaPago', title: 'Nuevo cuenta pago', type: 'header-sub'
                    },
                    {
                        path: 'editarCuentaPago', title: 'Editar cuenta pago', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'miscelaneo', title: 'Código misceláneo', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoMiscelaneo', title: 'Nuevo código misceláneo', type: 'header-sub'
                    },
                    {
                        path: 'editarMiscelaneo', title: 'Editar código misceláneo', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'almacen', title: 'Almacenes', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoAlmacen', title: 'Nuevo almacén', type: 'header-sub'
                    },
                    {
                        path: 'editarAlmacen', title: 'Editar almacén', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'proyectos', title: 'Proyectos', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoProyecto', title: 'Nuevo proyecto', type: 'header-sub'
                    },
                    {
                        path: 'editarProyecto', title: 'Editar proyecto', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'sistemasERP', title: 'Sistemas ERP', type: 'header-sub',
                children: [
                    {
                        path: 'nuevoSistemaerp', title: 'Nuevo sistema ERP', type: 'header-sub'
                    },
                    {
                        path: 'editarSistemaerp', title: 'Editar sistema ERP', type: 'header-sub'
                    }
                ]
            },
            {
                path: 'ordenCompra', title: 'Órdenes de compra', type: 'header-sub',
                children: [
                    {
                        path: 'nuevaOrden', title: 'Nueva órden de compra', type: 'header-sub'
                    },
                    {
                        path: 'editarOrden', title: 'Editar órden de compra', type: 'header-sub'
                    }
                ]
            },
        ]
    },
    {
        path: '/viaticos',
        permiso: 'Application/SF:SaludFiscal:Web:Viaticos:Modulo',
        title: 'Viáticos',
        type: 'sub',
        icontype: 'fa fa-suitcase',
        collapse: 'viaticos',
        children: [
            { path: 'administraciondeviajes', title: 'Administración de viajes', ab: 'AV' },
            { path: 'registroViajes', title: 'Registro de viajes', ab: 'RV' },
            { path: 'autorizarViaticos', title: 'Autorizar viáticos', ab: 'AV' },
            { path: 'administraciondeviaticos', title: 'Administración de viáticos', ab: 'RV' },
            { path: 'mensajesBuzon', title: 'Mensajes del buzón', ab: 'MB' },
            { path: 'configuraciones', title: 'Configuraciones', ab: 'CF' }
        ]
    },
    {
        path: '/triplematch',
        permiso: 'Application/SF:SaludFiscal:Web:TripleMatch:Modulo',
        title: 'Triple match',
        type: 'sub',
        icontype: 'fa fa-cubes',
        collapse: 'triplematch',
        children: [
            { path: 'facturasRecibidas', title: 'Facturas recibidas', ab: 'FF' },
            { path: 'buzonNoProcesados', title: 'Documentos del buzón no procesados', ab: 'B' }
        ]
    },
    {
        path: '/proveedores',
        permiso: 'Application/SF:SaludFiscal:Web:Proveedores:Modulo',
        title: 'Proveedores',
        type: 'sub',
        icontype: 'fa fa-id-card',
        collapse: 'proveedores',
        children: [
            {
                path: 'expedienteDash', title: 'Expediente', ab: 'E',
                children: [
                    {
                        path: 'configuracionExpediente', title: 'Configuración del expediente', type: 'header-sub',
                        children: [
                            { path: 'crear', title: 'Nuevo expediente', type: 'header-sub' },
                            { path: 'editar', title: 'Editar expediente', type: 'header-sub' }
                        ]
                    },
                    { path: 'plantillasExp', title: 'Plantillas expediente', type: 'header-sub' }
                ]
            },
            {
                path: 'proveedoresSf', title: 'Proveedores', ab: 'P',
                children: [
                    { path: 'crear', title: 'Nuevo proveedor', type: 'header-sub' },
                    { path: 'editar', title: 'Editar proveedor', type: 'header-sub' },
                    { path: 'listarOrdenCompra', title: 'Órdenes de compra', type: 'header-sub' },
                    {
                        path: 'expediente', title: 'Expediente', type: 'header-sub',
                        children: [
                            {
                                path: 'configuracion', title: 'Configuración expediente', type: 'header-sub',
                                children: [
                                    { path: 'crear', title: 'Nuevo expediente', type: 'header-sub' },
                                    { path: 'editar', title: 'Editar expediente', type: 'header-sub' }
                                ]
                            }
                        ]
                    }
                ]
            },

        ]
    },
    {
        path: '/facturasfinancieras',
        permiso: 'Application/SF:SaludFiscal:Web:FacturasFinancieras:Modulo',
        title: 'Facturas financieras',
        type: 'sub',
        icontype: 'fa fa-file-text',
        collapse: 'facturasfinancieras',
        children: [
            { path: 'facturas', title: 'Facturas financieras', ab: 'FF' },
            { path: 'buzonNoProcesados', title: 'Documentos del buzón no procesados', ab: 'B' }
        ]
    },
    {
        path: '/activosfijos',
        permiso: 'Application/SF:SaludFiscal:Web:ActivosFijos:Modulo',
        title: 'Activos fijos',
        type: 'link',
        icontype: 'fa fa-building'
    },
    {
        path: '/imss',
        permiso: 'Application/SF:SaludFiscal:Web:Imss:Modulo',
        title: 'IMSS',
        type: 'sub',
        icontype: 'fa fa-hospital-o',
        collapse: 'imss',
        children: [
            { path: 'opiniones', title: 'Opiniones infonavit', ab: 'ME' },
            { path: 'movimientosAfiliatorios', title: 'Movimientos afiliatorios', ab: 'ME' }
        ]
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
