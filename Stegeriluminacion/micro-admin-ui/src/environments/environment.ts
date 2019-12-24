// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {

  production: false,
  host: 'http://192.168.100.83:8601/stegeriluminacion',

  // MICRO_AUTH
  micro_auth: {
    base: "uaa",
    get: {
      cerrarsesion: "sesiones/cerrar"
    },
    post: {
      iniciarSesion: "oauth/token",
      obtenerLogFiltro: "log/filtro",
      obtenerSesionesFiltro: "sesiones/filtro",
      obtenerTrackerFiltro: "tracker/filtro",
      eliminarToken: "sesiones/token",
    }
  },

  // MICRO USUARIOS
  micro_usuarios: {
    base: "micro-usuarios",
    catalogos: {
      get: {
        timeUnits: "catalogos/timeunits"
      }
    },
    post: {
      obtenerUsuariosPublicoFiltro: "usuarios/publico/filtro",
      crearUsuarioPublico: "usuarios/publico",
      obtenerUsuariosAdminFiltro: "usuarios/admin/filtro",
      crearUsuarioAdmin: "usuarios/admin"
    },
    put: {
      actualizarUsuarioPublico: "usuarios/publico",
      actualizarUsuarioAdmin: "usuarios/admin"
    },
    get: {
      obtenerPermisosPublicos: "permisos/publico",
      obtenerPermisosAdmin: "permisos/admin"
    },
    delete: {
      borrarUsuarioPublico: "usuarios/publico",
      borrarUsuarioAdmin: "usuarios/admin"
    }
  }

};
