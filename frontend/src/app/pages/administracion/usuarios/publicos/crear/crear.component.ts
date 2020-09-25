import { HttpErrorResponse } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TreeviewConfig, TreeviewItem } from 'ngx-treeview';
import { PermisoPublicoDTO } from '../../../../../_dto/usuarios/PermisoPublico.Dto';
import { UsuariosPublicosDTO } from '../../../../../_dto/usuarios/UsuariosPublicos.Dto';
import { TimeUnitService } from '../../../../../_servicios/catalogos/timeunits.service';
import { PermisosPublicosService } from '../../../../../_servicios/usuarios/publicos/permisospublicos.service';
import { UsuariosPublicosService } from '../../../../../_servicios/usuarios/publicos/usuariospublicos.service';
import { UtilComponent } from '../../../../../_shared/util.component';
import { ValidacionesComponent } from '../../../../../_shared/validaciones/validaciones';

@Injectable()
export class PermisosTreeViewConfig extends TreeviewConfig {
  hasAllCheckBox = true;
  hasFilter = true;
  hasCollapseExpand = true;
  decoupleChildFromParent = false;
  maxHeight = 500;
}


@Component({
  templateUrl: 'crear.component.html',
  providers: [
    { provide: TreeviewConfig, useClass: PermisosTreeViewConfig }
  ]
})
export class CrearUsuarioComponent {

  public formulario: FormGroup;
  public isLoading: boolean = false;
  public showPassword: boolean = false;
  public usuariosPublicosDTO: UsuariosPublicosDTO;

  public permisosPublicosDTO: PermisoPublicoDTO[] = [];
  public timeUnitsDto: string[] = [];
  public permisos: TreeviewItem[];

  public permisosPublicosSeleccionados: PermisoPublicoDTO[] = [];

  constructor(private timeUnitService: TimeUnitService
    , private permisosPublicosService: PermisosPublicosService
    , private utilComponent: UtilComponent
    , private router: Router
    , private usuariosPublicosService: UsuariosPublicosService
    , private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.usuariosPublicosDTO = new UsuariosPublicosDTO;
    this.listAllTimeUnits();
    this.listAllPermisos();
    this.formulario = this.formBuilder.group({
      username: ['', Validators.compose([Validators.required, Validators.minLength(5)])],
      password: ['', Validators.compose(
        [
          // 1. Password Field is Required
          Validators.required,
          // 2. check whether the entered password has a number
          ValidacionesComponent.patternValidator(/\d/, { hasNumber: true }),
          // 3. check whether the entered password has upper case letter
          ValidacionesComponent.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
          // 4. check whether the entered password has a lower-case letter
          ValidacionesComponent.patternValidator(/[a-z]/, { hasSmallCase: true }),
          // 5. check whether the entered password has a special character
          ValidacionesComponent.patternValidator(new RegExp(/(?=.*[//,.?;<>':"!@#$%^&*(-=_+)|{}\[\]])/), { hasSpecialCharacters: true }),
          // 6. Has a minimum length of 6 characters 
          Validators.minLength(5)
        ]
      )],
      repetirPassword: ['', Validators.compose([Validators.required])],
      correo: ['', Validators.compose([Validators.required, Validators.minLength(5), Validators.email])],
      nombre: [''],
      apellido1: [''],
      apellido2: [''],
      permisos: ['', Validators.compose([Validators.required])],
      limitRequest: ['', Validators.compose([Validators.required, ValidacionesComponent.minValue(1)])],
      periodRequest: ['', Validators.compose([Validators.required, ValidacionesComponent.minValue(1)])],
      timeUnitRequest: ['MINUTES', Validators.compose([Validators.required])],
      timeUnitToken: ['MINUTES', Validators.compose([Validators.required])],
      tokenExpiration: ['', Validators.compose([Validators.required, ValidacionesComponent.minValue(1)])]
    }, {
      validator: ValidacionesComponent.MustMatch('password', 'repetirPassword')
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.formulario.controls; }


  onSubmit() {

    if (this.formulario.invalid) {
      this.utilComponent.showSweetAlert("Error", "Aun hay algunos datos que debes revisar antes de dar de alta un nuevo usuario,  revisa los campos", "warning");
      return;
    }
    this.utilComponent.showSweetAlertLoading("Usuarios", "Estamos creando tu usuario");
    let fingerprint = this.usuariosPublicosDTO.fingerPrintAuthentication;
    this.usuariosPublicosDTO = this.formulario.value;
    this.usuariosPublicosDTO.fingerPrintAuthentication = fingerprint;

    this.usuariosPublicosService.crear(this.usuariosPublicosDTO)
      .subscribe(resp => {
        this.isLoading = false;
        this.utilComponent.showSweetAlert("Creado", resp.mensaje, "success");
        this.regresar();
      }, error => {
        this.isLoading = false; 
        this.utilComponent.showSweetAlert("Error", this.utilComponent.trataErrores(error), "error");
      });
  }

  /*
  ================================================================
                   REINICIO DE VALORES
  ================================================================
  */
  reiniciarValores() {
    this.formulario.reset();
    this.permisosPublicosSeleccionados = [];
  }

  /*
  ================================================================
                    SELECCION DE PERMISOS
  ================================================================
  */
  onSelectedChange(event: any) {
    this.permisosPublicosSeleccionados = event;
    this.f.permisos.setValue(this.permisosPublicosSeleccionados);
    if (this.permisosPublicosSeleccionados.length > 0) {
      this.f.permisos.setErrors(null);
    } else {
      this.f.permisos.setErrors({ required: true });
    }
  }

  /*
  ================================================================
                           REGRESAR
  ================================================================
  */
  public regresar(): void {
    this.router.navigate(["/usuarios/publicos"]);
  }

  /*
  ================================================================
                     OBTENER TIME UNITS
  ================================================================
  */
  listAllTimeUnits() {
    this.isLoading = true;
    this.timeUnitsDto = [];
    this.timeUnitService.obtenerTodos()
      .subscribe(resp => {
        this.isLoading = false;
        this.timeUnitsDto = resp.cuerpo;
      }, (error: HttpErrorResponse) => {
        this.isLoading = false;
      });
  }

  /*
  ================================================================
                     OBTENER PERMISOS
  ================================================================
  */
  listAllPermisos() {
    this.isLoading = true;
    this.timeUnitsDto = [];
    this.permisosPublicosService.obtenerTodos()
      .subscribe(resp => {
        this.isLoading = false;
        this.permisosPublicosDTO = <PermisoPublicoDTO[]>resp.cuerpo;
        let nestedObjects = {};
        this.permisosPublicosDTO.forEach(permiso => {
          this.utilComponent.setNestedData(nestedObjects, permiso.etiqueta, ':', permiso);
          this.permisos = this.utilComponent.obtenerHijosPermiso(nestedObjects);
        });
      }, (error: HttpErrorResponse) => {
        this.isLoading = false;
      });
  }


}
