// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {

    // PARA DEV T0pVRTJmWG9iZTVidVpIWVlsRlRESEFHYnJrYTpkQm8xZEJ5RjBmd25vbTR4aEliQ2Jrcl9Va2Nh

  production: true,
  host_login:        'http://104.238.125.21:8081',
  host_principal:    'http://104.238.125.21:8081',
  host_repositorio:  'http://104.238.125.21:8081',
  host_listas_negras:'http://104.238.125.21:8081',
  host_proveedores:  'http://104.238.125.21:8081',
  host_erp:          'http://104.238.125.21:8081',
  host_triplematch:  'http://104.238.125.21:8081',
  host_clinica:      'http://104.238.125.21:8081',//'http://localhost:50850',http://
  host_viaticos:     'http://104.238.125.21:8081',
  host_buzon:        'http://104.238.125.21:8081',
  host_facturas:     'http://104.238.125.21:8081',
  host_correo:       'http://104.238.125.21:8081',
    /**
     *
     * MODULO PRINCIPAL
     *
     */
    principal: {
        base: 'principal',
        version_1_0: '1.0.0',
        // WSO" NO USADO
        wso2: {
            basic: "T0pVRTJmWG9iZTVidVpIWVlsRlRESEFHYnJrYTpkQm8xZEJ5RjBmd25vbTR4aEliQ2Jrcl9Va2Nh",
            post: {
                iniciarSesion: "token",
                cerrarSesion: "revoke"
            }
        },
        // LOGIN
        login: {
            basic: "T0pVRTJmWG9iZTVidVpIWVlsRlRESEFHYnJrYTpkQm8xZEJ5RjBmd25vbTR4aEliQ2Jrcl9Va2Nh",
            post: {
                iniciarSesion: "api/login/iniciarsesion",
                cerrarSesion: "api/login/cerrarsesion"
            }
        },
        // USUARIOS
        usuarios: {
            get: {
                obtenerTodos: "api/Usuarios/listAll",
                activar: "api/Usuarios/activate",
                obtenerUsuario: "api/Usuarios/findByUser"
            },
            post: {
                crear: "api/Usuarios/create",
                crearP: 'api/Usuarios/createassociateprovider'
            },
            put: {
                actualizar: "api/Usuarios/update"
            },
            delete: {
                borrar: "api/Usuarios/delete"
            }
        },
        responsables: {
            get: {
                obtenerTodosPorContrato: "api/Contratos/getResponsablesContratos"
            },
            post: {
                crear: "api/Responsables/crear"
            },
            put: {
                actualizar: "actualizar"
            },
            delete: {
                borrar: "borrar"
            }
        },
        perfiles: {
            get: {
                obtenerTodos: "api/Roles/listall",
                activar: "api/Roles/activate"
            },
            post: {
                crear: "api/Roles/create"
            },
            put: {
                actualizar: "api/Roles/update"
            },
            delete: {
                borrar: "api/Roles/delete"
            }
        },
        permisos: {
            get: {
                obtenerTodos: "api/Permisos/listall"
            }
        },
        contratos: {
            get: {
                obtenerTodos: "api/Contratos/getContracts",
                obtenerTodosFiltro: "api/Contratos/index",
                obtenerModulos: "api/Contratos/getModulos"
            },
            put: {
                actualizar: "api/Contratos/updateContract"
            },
            delete: {
                borrar: "api/Contratos/deleteContract"
            },
            post: {
                crear: "api/Contratos/postContract"
            }
        },
        empresas: {
            get: {
                obtenerTodos: "api/Empresas/index",
                obtenerTodas: "api/Empresas/getCompanies",
                obtenerTodasPorUsuario: "api/Empresas/getCompanyByUser"
            },
            put: {
                actualizar: "api/Empresas/updateCompany"
            },
            delete: {
                borrar: "api/Empresas/deleteCompany"
            },
            post: {
                crear: "api/Empresas/postCompany"
            }
        },
    },
    /**
     *
     * MODULO REPOSITORIO
     *
     */
    repositorio: {
        base: 'repositorio',
        version_1_0: '1.0.0',
        Facturas: {
            facturasE: {
                get: {
                    obtenerTodos: "api/Factura/issued_invoices",
                    obtenerFiltro: "api/Factura/issued_invoicesFltr"
                }
            },
            facturasR: {
                get: {
                    obtenerTodos: "api/Factura/received_invoices",
                    obtenerFiltro: "api/Factura/received_invoicesFltr"
                },
            },
            facturasProv:{
                get:{
                    obtener: 'api/Factura/received_invoicesPRVD',
                    obtenerDetalle: 'api/Repositorio/get_detail'
                }
            },
            pdfFacturas: {
                get: {
                    obtenerPDF: "api/Factura/Download_pdf_invoices"
                }
            },
            xslFacturasFiltroE: {
                get: {
                    obtenerXSL: "api/Factura/issued_invoicesRpt"
                }
            },
            xslFacturasFiltroR: {
                get: {
                    obtenerXSL: "api/Factura/received_invoicesRpt"
                }
            },
            zipFacturasFiltroE: {
                get: {
                    obtenerZIP: "api/Factura/issued_invoicesZip"
                }
            },
            zipFacturasFiltroR: {
                get: {
                    obtenerZIP: "api/Factura/received_invoicesZip"
                }
            }
        },
        Complementos: {
            complementosE: {
                get: {
                    obtenerTodos: "api/Pago/issued_complements",
                    obtenerFiltro: "api/Pago/issued_complementsFltr"
                }
            },
            complementosR: {
                get: {
                    obtenerTodos: "api/Pago/received_complements",
                    obtenerFiltro: "api/Pago/received_complementsFltr"
                }
            },
            pdfComplementos: {
                get: {
                    obtenerPDF: "api/Pago/Download_pdf_complements"
                }
            },
            xslComplemetnosE: {
                get: {
                    obtenerXSL: "api/Pago/issued_complementsRpt"
                }
            },
            xslComplemetnosR: {
                get: {
                    obtenerXSL: "api/Pago/received_complementsRpt"
                }
            },
            docsRel: {
                get: {
                    obtenerDoc: "api/Pago/related_documents"
                }
            },
            zipComplementosFiltroE: {
                get: {
                    obtenerZIP: "api/Pago/issued_complementsZip"
                }
            },
            zipComplementosFiltroR: {
                get: {
                    obtenerZIP: "api/Pago/received_complementsZip"
                }
            }
        },
        Nominas: {
            nomina: {
                get: {
                    obtenerTodos: "api/Nomina/issued_roster",
                    obtenerFiltro: "api/Nomina/issued_rosterFltr"
                }
            },
            pdfNominas: {
                get: {
                    obtenerPDF: "api/Nomina/Download_pdf_roster"
                }
            },
            xslNominas: {
                get: {
                    obtenerXSL: "api/Nomina/issued_rosterRpt"
                }
            },
            zipNominasFiltro: {
                get: {
                    obtenerZIP: "api/Nomina/issued_rosterZip"
                }
            }
        },
        declaraciones:{
            get:{
                obtenerTipo: "api/Declaracion/get_type_statement",
                obtenerDecl:"api/Declaracion/get_statement",
                obtenerPDF: "api/Declaracion/get_File"
            },
            post:{
                agregar: "api/Declaracion/add_statement"
            }
        },
        docXml: {
            get: {
                obtenerXml: "api/Repositorio/download_xml"
            }
        },
        filtroStatus: {
            get: {
                obtenerStatus: "api/Repositorio/get_status"
            }
        },
        Validar: {
            get: {
                validacion: "api/Repositorio/valid_cfdi"
            }
        },
        LogVal: {
            get: {
                logVal: "api/Repositorio/Log_cfdi"
            }
        }
    },
    /**
     *
     * MODULO PROVEEDORES
     *
     */
    proveedores: {
        base: 'proveedores',
        version_1_0: '1.0.0',
        expedientes: {
            get: {
                obtenerTodosFiltro: 'api/ConfiguracionExpediente/getfileproviderfilter'
            },
            post: {
                crear: 'api/ConfiguracionExpediente/createfileconfiguration',
                borrar: 'api/ConfiguracionExpediente/deletefileconfiguration'
            },
            put: {
                editar: 'api/ConfiguracionExpediente/updatefileconfiguration'
            }
        },
        expedienteSingular: {
            get:{
                obtenerExpedientePorProveedor: 'api/ConfiguracionExpediente/getdocumentsbyprovider'
            },
            post:{
                crear: 'api/ConfiguracionExpediente/createDocumentProveedor',
                eliminar:'api/ConfiguracionExpediente/deleteDocumentProveedor',
                agregarDeGeneral : 'api/ConfiguracionExpediente/createDocumentFromGeneral',
                aprobarTodos: 'api/ConfiguracionExpediente/aprobarDirectaProveedor',
            },
            put:{
                editar: 'api/ConfiguracionExpediente/editDocumentProveedor',
                cambiarTipoDocumentosProveedorURL : 'api/ConfiguracionExpediente/changeTypeDocumentProveedor',
                aceptarDocumentosProveedorURL: 'api/ConfiguracionExpediente/acceptDocumentProveedor',
                rechazarDocumentosProveedorURL: 'api/ConfiguracionExpediente/rejectDocumentProveedor'
            }
        },
        comentarios:{
            get:{
                obtenerTodos: 'api/ConfiguracionExpediente/getCommentByDocument'
            },
            post:{
                crear: 'api/ConfiguracionExpediente/createComment'
            }
        },
        proveedores: {
            get: {
                obtenerTodosFiltro: 'api/Proveedores/getprovidersfilter',
                obtenerCuentasFiltro: 'api/Cuenta/filteraccount',
                estatusProveedor: 'api/Proveedores/estatusAprobadoProveedor'
            },
            post: {
                crear: 'api/Proveedores/createprovider',
                borrar: 'api/Proveedores/deleteprovider'
            },
            put: {
                editar: 'api/Proveedores/editprovider',
                desvincular: 'api/Proveedores/unlinkprovider',
                vincular: 'api/Proveedores/linkprovider',
                reenviar: 'api/Proveedores/resendprovider',
                enviar: 'api/Proveedores/sendprovider',
                desaprobarTodos: 'api/Proveedores/disapproveAll'
            }
        },
        monedas: {
            get: {
                obtenerMonedasGnrlFiltro: 'api/Moneda/index'
            },
            delete: {
                borrar: 'deletecoin'
            }
        },
        portal: {
            get:{
                provedorPortal: 'api/ProveedoresPortal/getprovidersportalfilter',
                obtenerProveedor: 'api/ProveedoresPortal/getprovidersportal',
                obtenerDocumentosProveedor: 'api/ProveedoresPortal/getfileproviderfilter',
                obtenerDocumentProveedor: 'api/ProveedoresPortal/getfileprovider',
                obtenerDocumentoExpediente: 'api/ConfiguracionExpediente/getDocument'
            },
            post:{
                desaprovar: 'api/ProveedoresPortal/disapprovesuppliers',
                aprovar: 'api/ProveedoresPortal/approvedocument',
                obtenerComentarios: 'api/ProveedoresPortal/getcomments',
                obtenerDocumentos: 'api/ProveedoresPortal/getdocument',
                enviarInvitacion: '/api/ProveedoresPortal/sendmailinvitation',
                enviarCorreo: 'api/ProveedoresPortal/sendmailregistry',
                subirDocumentoExpediente: 'api/ConfiguracionExpediente/uploaddocument'
            },
            expediente:{
                get:{
                    obtenerConfiguraciones: 'api/ConfiguracionExpediente/getdocumentsbyprovider',
                    obtenerComentarios: 'api/ConfiguracionExpediente/getCommentByDocument'
                },
                post:{
                    enviarComentario: 'api/ConfiguracionExpediente/createComment'
                }
            }
        },
        plantillas:{
            get:{
                obtenerPlantillas: 'api/Plantilla/gettemplates',
                obtenerPlantilla: 'api/Plantilla/gettemplate',
                obtenerArchivoxPlantila: 'api/Plantilla/getfilesbytemplate'
            },
            post:{
                crear: 'api/Plantilla/createtemplate',
                editar: 'api/Plantilla/edittemplate',
                eliminar: 'api/Plantilla/deletetemplate',
                asociar: 'api/Plantilla/associatetemplatefile',
                desasociar: 'api/Plantilla/disassociatetemplatefile',
                cambiarArchivoPlantilla: 'api/Plantilla/switchtypetemplatefile',
                asociarPlantillaProveedor: 'api/Plantilla/associatetemplateprovider'
            }
        }
    },
    /**
     *
     * MODULO TRIPLE MATCH
     *
     */
    triplematch: {
        base: 'triplematch',
        version_1_0: '1.0.0',
        facturasfinancieras: {
            get: {
                obtenerComentarios: "api/Comentarios/listCommentsByInvoice",
                obtenerEstatus: "api/Facturas/GetStatusTM",
                obtenerEstatusERP: "api/Facturas/GetStatusERP",
                obtenerDetalle: "api/Facturas/GetDetailTM",
                obtenerLineas: "api/Facturas/GetLineInvoiceTM",
                obtenerTodos: "api/Facturas/listAll",
                obtenerHistorial: "api/Historial/listHistoryByInvoice",
                obtenerFiltroOrdenes: "api/OrdenesDeCompra/GetPO",
                obtenerLineasDeOrdenes: "api/OrdenesDeCompra/getLines",
                obtenerListaGRN: "api/Grn/GetGRN",
                obtenerOrdenesGRN: "api/Grn/GetGRNPO",
                obtenerLineasDeOrdenesGRN: "api/Grn/GetGRNPOLine",
                obtenerRecepcionesDisp: "api/OrdenesDeCompra/getPOLinesNotAssociated",
                obtenerDetalleLineaFactura: "api/OrdenesDeCompra/getPOLinesAssociatedByInvoiceLineId",
                obtenerDetallesLinea: "api/OrdenesDeCompra/getDetailByInvoiceLineId",
                sincronizacion: "api/ControlSincronizacion/CreateSyncControlPO"
            },
            post: {
                crearComentario: "api/Comentarios/createComment",
                agregarFactura: "api/Facturas/changeInvoiceStatusToInCapture",
                eliminarFactura: "api/Facturas/changeInvoiceStatusToNotAssigned",
                rechazarFact: "api/Facturas/changeInvoiceStatusToRejected",
                asociarDesasociar: "api/OrdenesDeCompra/associatePO",
                desasociarDesasociar: "api/OrdenesDeCompra/disassociatePO",
                asociarRecepcion: "api/OrdenesDeCompra/associateLine",
                desasociarRecepcion: "api/OrdenesDeCompra/disassociateLine",
                conciliar: "api/Facturas/reconciliationTM",
                revision: "api/Facturas/changeInvoiceStatusToInRevision",
                enviarERP: "api/Facturas/ProcessERP"
            }
        }
    },
    /**
     *
     * VIATICOS
     *
     */
    viaticos: {
        base: 'viaticos',
        version_1_0: '1.0.0',
        registroviajes: {
            get: {
                obtenerTodos: 'api/Viajes/gettravelrequest',
                obtenerComentarios: 'api/Comentarios/getcomments'
            },
            post: {
                crear_comentario: 'api/Comentarios/createcomments',
                crear: 'api/Viajes/posttravelrequest',
                editar: 'api/Viajes/puttravelrequest',
                eliminar: 'api/Viajes/deletetravelrequest',
                editarHoraio: 'api/Viajes/toggleitinerary',
                editarCita: '/api/Viajes/togglequote'
            }
        },
        configuraciones:{
            post:{
                crearF: 'api/ConceptoGasto/createspendingconcept',
                editarF: 'api/ConceptoGasto/editspendingconcept',
                eliminarF: 'api/ConceptoGasto/deletespendingconcept',
                actualizarViaticantesAuth: 'api/Autorizaciones/updateconfigurationviat',
                actualizarDeptoAuth: 'api/Autorizaciones/updateflowdepartment'
            },
            get:{
                obtenerF: '/api/ConceptoGasto/filterspendingconcept',
                obtenerUsuarios: 'api/Autorizaciones/getuserauth',
                obtenerViaticantesAuth: 'api/Usuarios/viatic_users',
                obtenerAuthRelViaticantes: 'api/Autorizaciones/getuserbyconfigviat',
                obtenerAuthRelDeptos: 'api/Autorizaciones/getflows',
            }
        }
    },
    /**
     *
     * MODULO ERP
     *
     */
    erp:{
        base:'erp',
        version_1_0:'1.0.0',
        articulos:{
            post:{
                crearArticulo: 'api/Articulos/createarticle',
                editarArticulo : 'api/Articulos/editarticle'
            },
            get:{
                filtrarArticulo: 'api/Articulos/filterarticle',
                unidadMedida: 'api/UnidadMedida/filtermeasuredunit'
            },
            delete:{
                borrarArticulo: 'api/Articulos/deletearticle'
            }
        },
        orden_compra:{
            get:{
                index: 'api/OrdenCompra/index'
            }
        },
        monedas: {
            post: {
                crear: 'api/Moneda/ingresaMoneda',
                borrar: 'api/Moneda/deleteMoneda'
            },
            get: {
                catalogo: 'api/Moneda/getCatMonedas',
                filtro: 'api/Moneda/index',
                obtenerMonedasGnrlFiltro: 'api/ConfiguracionExpediente/'
            },
            put: {
                editar: 'api/Moneda/editarMoneda'
            }
        },
        departamento: {
            get: {
                filtro: 'api/Departamento/filterdepartment'
            },
            post: {
                crear: 'api/Departamento/createdepartment',
                editar: 'api/Departamento/editdepartment',
                eliminar: 'api/Departamento/deletedepartment'
            }
        },
        cuentasERP: {
            get: {
                filtro: 'api/Cuenta/filteraccount'
            },
            post: {
                crear: 'api/Cuenta/createaccount',
                eliminar: 'api/Cuenta/deleteaccount',
                editar: 'api/Cuenta/editaccount'
            }
        },
        empleados: {
            get: {
                filtro: 'api/Empleado/filteremployee'
            },
            post: {
                crear: 'api/Empleado/createemployee',
                eliminar: 'api/Empleado/deleteemployee',
                editar: 'api/Empleado/editemployee'
            }
        },
        codigoPago: {
            get: {
                filtro: 'api/CodigoCosto/filtercodecost'
            },
            post: {
                crear: 'api/CodigoCosto/createcodecost',
                eliminar: 'api/CodigoCosto/deletecodecost',
                editar: 'api/CodigoCosto/editcodecost'
            }
        },
        unidadMedida: {
            get: {
                filtro: 'api/UnidadMedida/filtermeasuredunit',
                obtenerAsociadas: 'api/UnidadMedida/getassociatedmeasuredunit',
                obtenerListaAsociadas: 'api/UnidadMedida/getalldisassociatedmeasuredunit',
                unidadesSAT: 'api/UnidadMedidaSat/filtermeasuredunitsat'
            },
            post: {
                crear: 'api/UnidadMedida/createmeasuredunit',
                eliminar: 'api/UnidadMedida/deletemeasuredunit',
                editar: 'api/UnidadMedida/editmeasuredunit',
                asociar: 'api/UnidadMedida/associatemeasuredunit/',
                desasociar: 'api/UnidadMedida/disassociatemeasuredunit/'
            }
        },
        clientes: {
            get: {
                filtro: 'api/Cliente/filterclient'
            },
            post: {
                crear: 'api/Cliente/createclient',
                eliminar: 'api/Cliente/deleteclient',
                editar: 'api/Cliente/editclient'
            }
        },
        impuestos: {
            get: {
                filtro: 'api/Impuesto/filtertax'
            },
            post: {
                crear: 'api/Impuesto/createtax',
                eliminar: 'api/Impuesto/deletetax',
                editar: 'api/Impuesto/edittax'
            }
        },
        cuentaPago: {
            get: {
                filtro: 'api/CuentaPago/filterpaymentaccount'
            },
            post: {
                crear: 'api/CuentaPago/createpaymentaccount',
                eliminar: 'api/CuentaPago/deletepaymentaccount',
                editar: 'api/CuentaPago/editpaymentaccount'
            }
        },
        miscelaneo: {
            get: {
                filtro: 'api/CodigoMiscelaneo/filtermiscellaneouscode'
            },
            post: {
                crear: 'api/CodigoMiscelaneo/createmiscellaneouscode',
                eliminar: 'api/CodigoMiscelaneo/deletemiscellaneouscode',
                editar: 'api/CodigoMiscelaneo/editmiscellaneouscode'
            }
        },
        almacen: {
            get: {
                filtro: 'api/Almacen/filterwarehouse'
            },
            post: {
                crear: 'api/Almacen/createwarehouse',
                eliminar: 'api/Almacen/deletewarehouse',
                editar: 'api/Almacen/editwarehouse'
            }
        },
        proyecto: {
            get: {
                filtro: 'api/Proyecto/filterproject'
            },
            post: {
                crear: 'api/Proyecto/createproject',
                eliminar: 'api/Proyecto/deleteproject',
                editar: 'api/Proyecto/editproject'
            }
        },
        sistemaERP: {
            get: {
                filtro: 'api/SistemaExterno/filterexternalsystem'
            },
            post: {
                crear: 'api/SistemaExterno/createexternalsystem',
                eliminar: 'api/SistemaExterno/deleteexternalsystem',
                editar: 'api/SistemaExterno/editexternalsystem'
            }
        },
        ordenes: {
            get: {
                filtro: 'api/OrdenCompra/index',
                portal: 'api/OrdenCompra/filterOrdenCompra',
                lineas: 'api/OrdenCompra/getLineasOrdenCompra'
            },
            post: {
                crear: 'api/OrdenCompra/postOrdenCompra',
                eliminar: 'api/OrdenCompra/deleteOrdenCompra',
                editar: 'api/OrdenCompra/updateOrdenCompra'
            }
        },
        ordenServicio: {
            get: {
                filtro: 'api/OrdenServicio/filterserviceorder'
            },
            post: {
                crear: 'api/OrdenServicio/createserviceorder',
                eliminar: 'api/OrdenServicio/deleteserviceorder',
                editar: 'api/OrdenServicio/editserviceorder'
            }
        },
        controlSincronizacion: {
            get: {
                consultar: 'api/ControlSincronizacion/obtenercontrolsincronizacion'
            },
            post: {
                editar: 'api/Bitacora/editlog'
            }
        }
    },
    /**
     *
     * MODULO BUZON
     *
     */
    buzon:{
        base: 'buzon',
        version_1_0: '1.0.0',
        get:{
            obtenerMensajes: 'api/BuzonT/get_messages',
            obtenerMensaje: 'api/BuzonT/get_message',
            pdf: '/api/BuzonT/download_file'
        }
    },
    /**
     *
     * MODULO Listas Negras
     *
     */
    listasNegras:{
        base:'listas',
        version_1_0:'1.0.0',
        rfc:{
            art69:{
                obtenerArt69: 'api/ListasSAT/gettaxpayersart69'
            },
            art69B:{
                obtenerArt69B: 'api/ListasSAT/gettaxpayersart69B'
            }
        },
        clientesP:{
            art69:{
                obtenerArt69: 'api/ListasSAT/gettaxpayersart69bycompany'
            },
            art69B:{
                obtenerArt69B: 'api/ListasSAT/gettaxpayersart69Bbycompany'
            }
        },
        configuracion:{
            get:{
                obtener: 'api/Correos/getemaillist'
            },
            post:{
                crear: 'api/Correos/createemail',
                eliminar: 'api/Correos/deleteemail'
            }
        }

    },
    /**
     *
     * MODULO CLINICA FISCAL
     *
     */
    clinicaFiscal: {
        base: 'clinica',
        version_1_0: '1.0.0',
        carga: {
            get: {

            },
            post: {
                cargarArchivo: "api/Pagos/upload_file"
            }
        },
        reporte: {
            get: {
                obtenerMetodos: "api/Pagos/get_methods",
                obtenerFormas: "api/Pagos/get_shapes",
                obtenerPagosSinFact: "api/Pagos/get_unrelated_payments"
            },
            post: {
                obtenerFacturas: "api/Pagos/received_invoices",
                obtenerTotales: "api/Pagos/totals",
                obtenerReporte: "api/Pagos/get_report"
            }
        },
        reporteDetalle: {
            get: {
                obtenerComplementos: "api/Pagos/get_complements",
                obtenerPagos: "api/Pagos/get_payments",
                actualizarFechaConciliacion: 'api/Pagos/update_date'
            },
            post: {
                actualizarUUID: "api/Pagos/update_uuid"
            }
        }
    },
    /**
     *
     * MODULO FACTURAS FINANCIERAS
     *
     */
    facturas:{
        base:'facturasfinancieras',
        version_1_0:'1.0.0',
        comentarios:{
            post:{
                crear: 'api/ComentariosFacturas/createinvoicecomments'
            },
            get:{
                obtener: 'api/ComentariosFacturas/getinvoicecomments'
            }
        },
        detalles:{
            post: {
                crear: 'api/DetalleFactura/createinvoicedetail'
            },
            get: {
                obtener: 'api/DetalleFactura/getinvoicedetail'
            }
        },
        facturasFin:{
            post:{
                crear: 'api/FacturasFinancieras/createinvoicefinancial',
                edtiar: 'api/FacturasFinancieras/updateinvoicefinancial',
                crearLinea: 'api/FacturasFinancieras/createRowinvoicefinancial',
                borrarLinea: 'api/FacturasFinancieras/deleteinvoiceline',
                borrarFactura: 'api/FacturasFinancieras/deleteinvoicefinancial',
                enviar: 'api/FacturasFinancieras/sendfinancialinvoicetotransformer'
            },
            get:{
                obtenerProv: 'api/FacturasFinancieras/filterinvoicesproviders',
            }
        },
        facturasFiltro: {
            get: {
                obtenerFacturas: 'api/FacturasFinancieras/filterinvoices',
                obtenerEstatus: 'api/FacturasFinancieras/getstatusfinancialinvoice'
            }
        }
    },
    correo: {
        base: 'Correo',
        version_1_0: '1.0.0',
        get: {
            obtenerCorreos: 'api/Correo/getemail',
            obtenerDetalleCorreo: 'api/Correo/getdetailemail'
        }
    }
};
