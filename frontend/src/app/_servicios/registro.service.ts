import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UtilComponent } from '../_shared/util.component';

@Injectable({
  providedIn: 'root'
})
export class RegistroService {

  constructor(private http: HttpClient, public utilComponent: UtilComponent) { }

}
