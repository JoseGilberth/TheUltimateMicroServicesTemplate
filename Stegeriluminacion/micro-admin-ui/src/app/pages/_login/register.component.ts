import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import swal from "sweetalert2";
import { configuraciones } from '../../../environments/configuraciones';
import { UtilComponent } from '../../_shared/util.component';
import { ValidacionesComponent } from '../../_shared/validaciones/validaciones';

@Component({
    selector: 'app-register-cmp',
    templateUrl: './register.component.html'
})

export class RegisterComponent implements OnInit, OnDestroy {
    test: Date = new Date();

    /* CONFIGURACIONES */
    configuraciones: any;
    validacionesComponent: any; 
    public translate: TranslateService;

    error = false;
    form = new FormGroup(
        {
            password: new FormControl('', Validators.maxLength(8)),
            confirm: new FormControl('', Validators.maxLength(8)),
        },
        passwordMatchValidator
    );

    form2 = new FormGroup({
        usuario: new FormControl('', Validators.required),
        nombre: new FormControl('', Validators.required),
        correo: new FormControl('', [Validators.required, Validators.email]),
    });

    constructor(private util: UtilComponent, private route: ActivatedRoute, translate: TranslateService, private router: Router) {
        this.translate = translate;
        this.configuraciones = configuraciones;
        this.validacionesComponent = ValidacionesComponent;
    }

    ngOnInit() { 
    }
    ngOnDestroy() {

    }

    passwordErrorMatcher = {
        isErrorState: (control: FormControl, form: FormGroupDirective): boolean => {
            const controlInvalid = control.touched && control.invalid;
            const formInvalid = control.touched && this.form.get('confirm').touched && this.form.invalid;
            return controlInvalid || formInvalid;
        }
    };

    confirmErrorMatcher = {
        isErrorState: (control: FormControl, form: FormGroupDirective): boolean => {
            const controlInvalid = control.touched && control.invalid;
            const formInvalid = control.touched && this.form.get('password').touched && this.form.invalid;
            return controlInvalid || formInvalid;
        }
    };

    getErrorMessage(controlName: string) {
        if (this.form.controls[controlName].hasError('minlength')) {
            return 'Must be at least 2 characters';
        }

        return 'La contraseña no coincide';
    }

    verifica() {
        let ward = true; 
        return ward;
    }

    crear() {
        this.translate.get('principal.registro.crearM').subscribe((mensajes: string) => {
            this.util.showLoading(mensajes['titulo'], mensajes['creando'], 'info');
            
        });
    }

}

function passwordMatchValidator(g: FormGroup) {
    const password = g.get('password').value;
    const confirm = g.get('confirm').value;
    return password === confirm ? null : { mismatch: true };
}

