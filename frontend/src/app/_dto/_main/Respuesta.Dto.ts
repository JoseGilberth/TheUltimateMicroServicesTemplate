
export class Respuesta<T> {
    codigoHttp: number;
    cuerpo?: T;
    mensaje: string;
}