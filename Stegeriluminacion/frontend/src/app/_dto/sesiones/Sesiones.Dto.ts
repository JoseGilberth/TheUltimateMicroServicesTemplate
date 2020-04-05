
export class SesionesDTO {
    expiresIn: number;
    tokenType: string;
    usuario: string;
    clientId: string;
    token: string;
    expiration: Date;
    scope: string[];
    additionalInformation: Map<string, any>;
}