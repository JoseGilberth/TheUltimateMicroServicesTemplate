// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {

  production: false,
  isMicroserviceOriented: true,
  host: 'http://localhost:8443/micro-template',
  websocket:'http://localhost:8601',

  // MICRO_AUTH
  micro_auth: {
    base: "micro-auth",
    post: {
      iniciarSesion: "oauth/token",
    },
    log: {
      base: 'log',
      post: {
        filtro: 'filtro'
      }
    },
    sesiones: {
      base: 'sesiones',
      get: {
        cerrar: 'cerrar'
      },
      post: {
        filtro: 'filtro',
        token: 'token'
      }
    },
    request_of_login: {
      base: 'request/login'
    }
  },


  // MICRO CATALOGOS
  micro_catalogos: {
    base: "micro-catalogos",
    catalogos: {
      base: "catalogos",
      timeUnits: {
        base: "timeunits"
      }
    }
  },

  // MICRO USUARIOS
  micro_usuarios: {
    base: "micro-usuarios",
    usuarios_publicos: {
      base: "usuarios/publico",
      post: {
        filtro: "filtro"
      }
    },
    permisos_publico: {
      base: "permisos/publico"
    },
    usuarios_admin: {
      base: "usuarios/admin",
      post: {
        filtro: "filtro"
      }
    },
    permisos_admin: {
      base: "permisos/admin"
    }
  },

  // MICRO ADMIN
  micro_admin: {
    base: "micro-admin",
    database: {
      base: "database",
      get: {
        metrics: "metrics"
      }
    },
    ocr: {
      base: "ocr"
    }
  }

};
