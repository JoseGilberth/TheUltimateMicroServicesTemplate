

export const configuraciones = {

    principal: {
        usuarios: {
            usuario: {
                required: "required",
                min: 2,
                max: 10,
                pattern: "^[a-zA-Z0-9 áéíóúÁÉÍÓÚ]*$" // VALIDA SOLO LETRAS Y ESPACIOS EN BLANCO
            },
            password: {
                required: "required",
                min: 1,
                max: 8,
                pattern: "^(?=.{0,8}$)(([a-zA-Z0-9 \\\]\\\[!#$%&()*+,-.:;<=>?@\\^_{|}~])\\2?(?!\\2))+$" // VALIDA SOLO LETRAS Y ESPACIOS EN BLANCO
            },
            passwordconfim: {
                required: "required",
                min: 1,
                max: 80,
                pattern: "" // VALIDA SOLO LETRAS Y ESPACIOS EN BLANCO
            },
            nombre: {
                required: "required",
                min: 1,
                max: 50,
                pattern: "^[a-zA-Z áéíóúÁÉÍÓÚ]*$" // VALIDA SOLO LETRAS Y ESPACIOS EN BLANCO
            },
            primer_apellido: {
                required: "required",
                min: 1,
                max: 30,
                pattern: "^[a-zA-Z áéíóúÁÉÍÓÚ]*$" // VALIDA SOLO LETRAS Y ESPACIOS EN BLANCO
            },
            segundo_apellido: {
                required: "required",
                min: 1,
                max: 30,
                pattern: "^[a-zA-Z áéíóúÁÉÍÓÚ]*$" // VALIDA SOLO LETRAS Y ESPACIOS EN BLANCO
            },
            correo: {
                required: "required",
                min: 1,
                max: 73,
                pattern: ""// VALIDA SOLO LETRAS Y ESPACIOS EN BLANCO
            }
        },
        perfiles: {
            nombre: {
                required: "required",
                min: 1,
                max: 50,
                pattern: "^[a-zA-Z áéíóúÁÉÍÓÚ]*$" // VALIDA SOLO LETRAS Y ESPACIOS EN BLANCO
            },
            descripcion: {
                required: "required",
                min: 1,
                max: 120,
                pattern: "^[a-zA-Z áéíóúÁÉÍÓÚ,]*$" // VALIDA SOLO LETRAS Y ESPACIOS EN BLANCO
            }
        }
    },
    erp:{
        departamentos: {
            numeros: {
                min: 1,
                max: 50,
                pattern: "^[0-9]*$"
            }
        },
        costo:{
            min: 1,
            max: 50,
            pattern: '(\\d*[^a-zA-Z\\|\\°\\!\\"\\#\\$\\%\\&\\/\\(\\)\\,\\:\\;=\\-\\>\\<\\_\\[\\]\\¨\\*\\´\\?\\¡ñÑ])(\\.?)(\\d{1,2})?'
        }
    },
    static: {
        empresas: "EMPRESA",
        proveedor: "PROVEEDOR",
        token: "TOKEN",
        usuario: "USUARIO",
        roles: "ROLES",
        articulos: "ARTICULOS",
        monedas: "MONEDAS",
        departamento: "DEPARTAMENTO",
        cuentasERP: "CUENTASERP",
        empleados: "EMPLEADOS",
        costoCod: "CODIGOCOSTO",
        unidadMedida: "UMEDIDA",
        cliente: "CLIENTE",
        impuestos: "IMPUESTOS",
        cuentaPago: "CUENTAPAGO",
        miscelaneo: "MISCELANEO",
        almacen: "ALMACEN",
        proyecto: "PROYECTOS",
        sistemas: "SISTEMAS",
        ordenes: "ORDCOMPRA",
        oServicios: "ORDSERVICIO",
        permisos: "PERMISOS",
        registroviajes: "REGISTRODEVIAJES",
        administracionviajes: "ADMINISTRACIONDEVIAJES",
        conceptosPago: "CONCEPTOS",
        sistemaERP: "SISTEMAERP",
        detalleFact: "DETALLEFACT",
        documentoExpediente: "DOCEXPEDIENTE",
        configExpediente: "CONFIGEXP"
    },
    botones: { 
        boton_resumen: "fa fa-list-alt" ,
        boton_expandir: "fa fa-bars" ,
        boton_detalle: "fa fa-book" ,
        boton_configurar: "fa fa-cog" ,
        boton_buscar: "fa fa-search" ,
        boton_activar: "fa fa-check" ,
        boton_agregar: "fa fa-plus" ,
        boton_editar: "fa fa-edit" ,
        boton_eliminar: "fa fa-trash" ,
        boton_filtro: "fa fa-filter",
        boton_excel: "fa fa-file-excel-o",
        boton_descargar_varios: "fa fa-file-archive-o",
        boton_pdf: 'fa fa-file-pdf-o',
        boton_comentario: 'fa fa-comments',
        boton_orden_compra: 'fa fa-file-text-o',
        boton_xml: 'fa fa-file-code-o',
        boton_historial: 'fa fa-history',
        boton_moneda: 'fa fa-usd',
        boton_desaprobar: 'fa fa-thumbs-o-down',
        boton_aprobar: 'fa fa-thumbs-o-up',
        boton_enviar: 'fa fa-paper-plane',
        boton_refresh: 'fa fa-refresh',
        boton_exchange: 'fa fa-exchange',
        boton_mensaje: 'fa fa-envelope-o',
        boton_Rmensaje: 'fa fa-envelope',
        boton_vincular: 'fa fa-link',
        boton_desVincular: 'fa fa-chain-broken',
        boton_cerrar: 'fa fa-times',
        boton_file_text: 'fa fa-file-text',
        boton_users: 'fa fa-users',
        boton_build: 'fa fa-building',
        boton_factory: 'fa fa-industry',
        boton_calculator: 'fa fa-calculator',
        boton_ticket: 'fa fa-ticket',
        boton_tag: 'fa fa-tags',
        boton_balance: 'fa fa-balance-scale',
        boton_clientes: 'fa fa-address-book',
        boton_nota: 'fa fa-sticky-note',
        boton_pago: 'fa fa-credit-card-alt',
        boton_articulos: 'fa fa-cubes',
        boton_orden: 'fa fa-shopping-cart',
        boton_almacen: 'fa fa-archive',
        boton_config: 'fa fa-cog',
        boton_erp: 'fa fa-sitemap',
        boton_informacion: 'fa fa-info',
        boton_envioERP: 'fa fa-paper-plane',
        boton_rechazar: 'fa fa-close',
        boton_flujos: 'fa fa-random',
        boton_upload: 'fa fa-upload',
        boton_itinerario: 'fa fa-plane',
        boton_ban: 'fa fa-ban',
        boton_factura: 'fa fa-file-o',
        boton_bed: 'fa fa-bed',
        boton_conciliar: 'fa fa-handshake-o',
        boton_folder: 'fa fa-folder-open'
    },
    datatable: {
        pageLength: 10,
        pagingType: "full_numbers",
        scrollY: "600px",
        scrollYmodal: "200px",
        lengthMenu: [
            [10, 25, 50],
            [10, 25, 50]
        ]
    },
    small_datatable: {
        pageLength: 5,
        pagingType: "full_numbers",
        scrollY: "300px",
        scrollYmodal: "200px",
        lengthMenu: [
            [5, 10, 15],
            [5, 10, 15]
        ]
    },
    modulo: {
        erp: {
            id: 1,
            sub_modulos: {
                cuentas: {
                    id: 1
                },
                monedas: {
                    id: 2
                },
                impuestos: {
                    id: 3
                },
                clientes: {
                    id: 4
                },
                departamentos: {
                    id: 5
                },
                proveedores: {
                    id: 6
                },
                unidades: {
                    id: 7
                }
            }
        }
    },

};
