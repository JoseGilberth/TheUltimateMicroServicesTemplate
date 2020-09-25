import { FingerPrintAuthenticationDTO } from './FingerPrintAuthentication.Dto';

export class UsuariosDTO {

    id: number | null;
    username: string;
    password: string;
    repetirPassword: string;
    huella: string;
    fingerPrintAuthentication: FingerPrintAuthenticationDTO;
    correo: string;
    nombre: string;
    apellido1: string;
    apellido2: string;
    fechaAlta: string;
    ultimaActualizacion: string;
    enabled: boolean;

    constructor() {
        this.username = '';
        this.correo = '';
        this.nombre = '';
        this.password = '';
    }

}