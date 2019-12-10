import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
 import { Respuesta } from 'src/app/_dto/_main/Respuesta.Dto';
import { UtilComponent } from 'src/app/_shared/util.component';
import { environment } from 'src/environments/environment';
 


@Injectable({
  providedIn: 'root'
})
export class RegistroService {


 
  constructor(private http: HttpClient, public utilComponent: UtilComponent) { }
 

}
