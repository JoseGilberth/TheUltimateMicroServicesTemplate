import { Token } from './Token.Dto';

export class RequestOfLoginDTO extends Token {

    id: number;
    uuid: string;
    usuario: string;
    fechaAlta: string;
    fechaActualizacion: string;
}   
