// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {

  production: false,
  host: 'http://localhost:8601/stegeriluminacion',

  // MICRO_AUTH
  micro_auth: {
    base: "uaa",
    post: {
      iniciarSesion: "oauth/token"
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
      obtenerUsuariosAdminFiltro: "usuarios/publico/filtro",
      crearUsuarioAdmin: "usuarios/publico"
    },
    put: {
      actualizarUsuarioPublico: "usuarios/publico",
      actualizarUsuarioAdmin: "usuarios/publico"
    },
    get: {
      obtenerPermisosPublicos: "permisos/publico",
      obtenerPermisosAdmin: "permisos/publico"
    },
    delete: {
      borrarUsuarioPublico: "usuarios/publico",
      borrarUsuarioAdmin: "usuarios/publico"
    }
  }


};
