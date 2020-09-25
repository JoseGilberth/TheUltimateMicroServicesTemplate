import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { OcrDTO } from '../../_dto/ocr/Ocr.Dto';
import { Respuesta } from '../../_dto/_main/Respuesta.Dto';

@Injectable({
  providedIn: 'root'
})
export class OcrService {

  private host: string = `${environment.host}`;
  private microServiceBase: string = environment.isMicroserviceOriented ? `${this.host}/${environment.micro_admin.base}` : `${this.host}`;
  private moduleBase: string = `${this.microServiceBase}/${environment.micro_admin.ocr.base}`;

  constructor(private http: HttpClient) { }

  post(ocrDTO: OcrDTO): Observable<Respuesta<string>> {
    return this.http.post<Respuesta<string>>(`${this.moduleBase}`, ocrDTO);
  }


}
