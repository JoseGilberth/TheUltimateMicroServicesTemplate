import { FingerPrintFmdIndiceDerechoDTO } from './FingerPrintFmdIndiceDerecho.Dto';
import { FingerPrintFmdIndiceIzquierdoDTO } from './FingerPrintFmdIndiceIzquierdo.Dto';
import { FingerPrintFmdMedioDerechoDTO } from './FingerPrintFmdMedioDerecho.Dto';
import { FingerPrintFmdMedioIzquierdoDTO } from './FingerPrintFmdMedioIzquierdo.Dto';
import { FingerPrintFmdPulgarDerechoDTO } from './FingerPrintFmdPulgarDerecho.Dto';
import { FingerPrintFmdPulgarIzquierdoDTO } from './FingerPrintFmdPulgarIzquierdo.Dto';

export class FingerPrintAuthenticationDTO {

    fingerPrintFmdPulgarDerecho: FingerPrintFmdPulgarDerechoDTO[];
    fingerPrintFmdIndiceDerecho: FingerPrintFmdIndiceDerechoDTO[];
    fingerPrintFmdMedioDerecho: FingerPrintFmdMedioDerechoDTO[];

    fingerPrintFmdPulgarIzquierdo: FingerPrintFmdPulgarIzquierdoDTO[];
    fingerPrintFmdIndiceIzquierdo: FingerPrintFmdIndiceIzquierdoDTO[];
    fingerPrintFmdMedioIzquierdo: FingerPrintFmdMedioIzquierdoDTO[];

} 